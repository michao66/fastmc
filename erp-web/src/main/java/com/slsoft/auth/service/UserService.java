package com.slsoft.auth.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fastmc.code.jpa.dao.SpringJpaDao;
import cn.fastmc.core.jpa.service.BaseService;

import com.slsoft.auth.dao.PrivilegeDao;
import com.slsoft.auth.dao.RoleDao;
import com.slsoft.auth.dao.UserDao;
import com.slsoft.auth.dao.UserLogonLogDao;
import com.slsoft.auth.dao.UserOauthDao;
import com.slsoft.auth.dao.UserR2RoleDao;
import com.slsoft.auth.entify.Privilege;
import com.slsoft.auth.entify.Role;
import com.slsoft.auth.entify.User;
import com.slsoft.auth.entify.UserLogonLog;
import com.slsoft.auth.entify.UserOauth;
import com.slsoft.auth.entify.UserR2Role;
import com.slsoft.core.security.AclService;
import com.slsoft.core.security.AuthUserDetails;

@SuppressWarnings("deprecation")
@Service
@Transactional
public class UserService extends BaseService<User, String> {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserOauthDao userOauthDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PrivilegeDao privilegeDao;

    @Autowired
    private UserR2RoleDao userR2RoleDao;

    @Autowired
    private UserLogonLogDao userLogonLogDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired(required = false)
    private AclService aclService;
    //@Autowired(required = false)
    //private IdentityService identityService;

    @Override
    protected SpringJpaDao<User, String> getEntityDao() {
        return userDao;
    }

    @Override
    protected void preInsert(User entity) {
        super.preInsert(entity);
      
       
    }



    public User findBySigninid(String signinid) {
        return findByProperty("signinid", signinid);
    }

    @Override
    public User save(User user) {
    	 if (user.isNew()) {
             user.setUserPin(user.getSigninid());
             user.setUserUid(RandomStringUtils.randomNumeric(10));
             super.save(user);
         }else{
        	 super.update(user,"password"); 
         }
       
        return user;
    }

    /**
     * 用户注册
     * 
     * @param rawPassword
     *            原始密码
     * @param user
     *            用户数据对象
     * @return
     */
    public User save(User user, String rawPassword) {
        if (StringUtils.isNotBlank(rawPassword)) {
            // 密码修改后更新密码过期时间为6个月
            user.setCredentialsExpireTime(new DateTime().plusMonths(6).toDate());
            user.setPassword(encodeUserPasswd(user, rawPassword));
        }
        return save(user);
    }

    public boolean validatePassword(String signinid, String rawPassword) {
        User user = findBySigninid(signinid);
        //用户账号不存在，直接返回密码验证失败
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(encodeUserPasswd(user, rawPassword));
    }
//785b634880f533e38a1f27be283fe962
   // 21232f297a57a5a743894a0e4a801fc3
    public String encodeUserPasswd(User user, String rawPassword) {
        return passwordEncoder.encodePassword(rawPassword, user.getUserUid());
    }

    public void updateRelatedRoleR2s(String id, String... roleIds) {
        updateRelatedR2s(id, roleIds, "userR2Roles", "role");
     
    }

    
    @Transactional(readOnly = true)
    public List<Privilege> findRelatedPrivilegesForUser(User user) {
        return privilegeDao.findPrivilegesForUser(user);
    }

    @Async
    public void userLogonLog(UserLogonLog userLogonLog) {
        if (userLogonLogDao.findByHttpSessionId(userLogonLog.getHttpSessionId()) != null) {
            return;
        }
        userLogonLogDao.save(userLogonLog);
    }

    public User findByOauthUser(String username) {
        UserOauth userOauth = userOauthDao.findByUsername(username);
        if (userOauth == null) {
            return null;
        } else {
            return userOauth.getUser();
        }
    }

    public Long findUserCount() {
        return userDao.findUserCount();
    }

    /**
     * 加载用户权限数据对象
     * 
     * @param username
     * @return
     */
    public UserDetails loadUserDetails(String username) {
        logger.debug("Loading user details for: {}", username);

        User user = null;
        // 添加邮件登录支持
        if (username.indexOf("@") > -1) {
            user = findByProperty("email", username);
        }
        if (user == null) {
            user = findByProperty("signinid", username);
        }

        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }

        boolean enabled = user.getEnabled() == null ? true : user.getEnabled();
        boolean accountNonLocked = user.getAccountNonLocked() == null ? true : user.getAccountNonLocked();
        Date now = new Date();
        boolean credentialsNonExpired = user.getCredentialsExpireTime() == null ? true : user.getCredentialsExpireTime().after(now);
        boolean accountNonExpired = user.getAccountExpireTime() == null ? true : user.getAccountExpireTime().after(now);

        if (!enabled) {
            throw new DisabledException("User '" + username + "' disabled");
        }
        if (!credentialsNonExpired) {
            throw new CredentialsExpiredException("User '" + username + "' credentials expired");
        }
        if (!accountNonLocked) {
            throw new LockedException("User '" + username + "' account locked");
        }
        if (!accountNonExpired) {
            throw new AccountExpiredException("User '" + username + "' account expired");
        }

        Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();
        Iterable<UserR2Role> r2s = userR2RoleDao.findEnabledRolesForUser(user);
        for (UserR2Role userR2Role : r2s) {
            String roleCode = userR2Role.getRole().getCode();
            dbAuthsSet.add(new SimpleGrantedAuthority(roleCode));
        }
        dbAuthsSet.add(new SimpleGrantedAuthority(Role.ROLE_ANONYMOUSLY_CODE));

        if (logger.isDebugEnabled()) {
            logger.debug("User role list for: {}", username);
            for (GrantedAuthority ga : dbAuthsSet) {
                logger.debug(" - " + ga.getAuthority());
            }
        }

        AuthUserDetails authUserDetails = new AuthUserDetails(username, user.getPassword(), enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, dbAuthsSet);
        authUserDetails.setUserUid(user.getUserUid());

        authUserDetails.setEmail(user.getEmail());

        
        // 处理用户拥有的权限代码集合

        Set<String> privilegeCodeSet = new HashSet<String>();
        List<Privilege> privileges = privilegeDao.findPrivilegesForUser(user);
        if (logger.isDebugEnabled()) {
            logger.debug("User privilege list for: {}", username);
            for (Privilege privilege : privileges) {
                logger.debug(" - {} : {}", privilege.getCode(), privilege.getUrl());
            }
        }
        for (Privilege privilege : privileges) {
            privilegeCodeSet.add(privilege.getCode().trim());
        }
        authUserDetails.setPrivilegeCodes(privilegeCodeSet);

        return authUserDetails;
    }

   

    public void resetPassword(User user, String rawPassword) {
        user.setRandomCode(null);
        save(user, rawPassword);
    }
    
    //785b634880f533e38a1f27be283fe962
    // 21232f297a57a5a743894a0e4a801fc3
     //public String encodeUserPasswd(User user, String rawPassword) {
       //  return passwordEncoder.encodePassword(rawPassword, user.getUserUid());
     //}
    public  static void main(String args[]){
    	Md5PasswordEncoder	passwordEncoder = new Md5PasswordEncoder();
      	System.out.println(passwordEncoder.encodePassword("admin", null));
    }

}
