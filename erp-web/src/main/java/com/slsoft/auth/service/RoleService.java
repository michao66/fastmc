package com.slsoft.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fastmc.code.jpa.dao.SpringJpaDao;
import cn.fastmc.core.jpa.service.BaseService;

import com.google.common.collect.Lists;
import com.slsoft.auth.dao.PrivilegeDao;
import com.slsoft.auth.dao.RoleDao;
import com.slsoft.auth.dao.RoleR2PrivilegeDao;
import com.slsoft.auth.dao.UserR2RoleDao;
import com.slsoft.auth.entify.Role;
import com.slsoft.auth.entify.User;
import com.slsoft.auth.entify.UserR2Role;

@Service
@Transactional
public class RoleService extends BaseService<Role, String> {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PrivilegeDao privilegeDao;

    @Autowired
    private UserR2RoleDao userR2RoleDao;

    @Autowired
    private RoleR2PrivilegeDao roleR2PrivilegeDao;

    @Override
    protected SpringJpaDao<Role, String> getEntityDao() {
        return roleDao;
    }

    @Transactional(readOnly = true)
    public List<Role> findAllCached() {
        return roleDao.findAllCached();
    }

    @Transactional(readOnly = true)
    public List<Role> findR2RolesForUser(User user) {
        List<Role> roles = Lists.newArrayList();
        Iterable<UserR2Role> r2s = userR2RoleDao.findEnabledRolesForUser(user);
        for (UserR2Role r2 : r2s) {
            roles.add(r2.getRole());
        }
        return roles;
    }

    @CacheEvict(value = "SpringSecurityCache", allEntries = true)
    public void updateRelatedPrivilegeR2s(String roleId, String[] r2Ids) {
        updateRelatedR2s(roleId, r2Ids, "roleR2Privileges", "privilege");
    }
    
   
    @Override
    @CacheEvict(value = "SpringSecurityCache", allEntries = true)
    public Role save(Role entity) {
        return super.save(entity);
    }

    @Override
    @CacheEvict(value = "SpringSecurityCache", allEntries = true)
    public void delete(Role entity) {
        super.delete(entity);
    }

    @Override
    @CacheEvict(value = "SpringSecurityCache", allEntries = true)
    public List<Role> save(Iterable<Role> entities) {
        return super.save(entities);
    }

    @Override
    @CacheEvict(value = "SpringSecurityCache", allEntries = true)
    public void delete(Iterable<Role> entities) {
        super.delete(entities);
    }
}
