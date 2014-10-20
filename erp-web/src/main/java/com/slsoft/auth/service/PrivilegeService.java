package com.slsoft.auth.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fastmc.code.jpa.dao.SpringJpaDao;
import cn.fastmc.core.jpa.service.BaseService;
import cn.fastmc.core.jpa.specification.Specifications;
import cn.fastmc.core.pagination.GroupPropertyFilter;

import com.slsoft.auth.dao.PrivilegeDao;
import com.slsoft.auth.dao.RoleDao;
import com.slsoft.auth.dao.RoleR2PrivilegeDao;
import com.slsoft.auth.entify.Privilege;
import com.slsoft.auth.entify.Role;
import com.slsoft.auth.entify.RoleR2Privilege;

@Service
@Transactional
public class PrivilegeService extends BaseService<Privilege, String> {

    private final Logger logger = LoggerFactory.getLogger(PrivilegeService.class);

    @Autowired
    private PrivilegeDao privilegeDao;

    @Autowired
    private RoleR2PrivilegeDao roleR2PrivilegeDao;

    @Autowired
    private RoleDao roleDao;



    @Value("${auth.control.level}")
    private String authControlLevel;

    @Override
    protected SpringJpaDao<Privilege, String> getEntityDao() {
        return privilegeDao;
    }

    @Transactional(readOnly = true)
    public List<Privilege> findAllCached() {
        return privilegeDao.findAllCached();
    }

    @Transactional(readOnly = true)
    public List<String> findDistinctCategories() {
        return privilegeDao.findDistinctCategories();
    }

    @Transactional(readOnly = true)
    public Page<Privilege> findUnRelatedPrivilegesForRole(final String roleId, final GroupPropertyFilter groupFilter,
            Pageable pageable) {
        Specification<Privilege> specification = new Specification<Privilege>() {
            
            public Predicate toPredicate(Root<Privilege> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = Specifications.get(groupFilter).toPredicate(root, query, builder);
                Subquery<RoleR2Privilege> sq = query.subquery(RoleR2Privilege.class);
                Root<RoleR2Privilege> r2 = sq.from(RoleR2Privilege.class);
                sq.where(builder.equal(r2.get("privilege"), root), builder.equal(r2.get("role").get("id"), roleId)).select(r2);
                return builder.and(predicate, builder.not(builder.exists(sq)));
            }
        };
        return privilegeDao.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public List<RoleR2Privilege> findRelatedRoleR2PrivilegesForPrivilege(String privilegeId) {
        return roleR2PrivilegeDao.findByPrivilege_Id(privilegeId);
    }

    //加载所有资源与权限的关系   
    @Transactional(readOnly = true)
    @Cacheable("SpringSecurityCache")
    public Map<String, Collection<ConfigAttribute>> loadResourceDefine() {
        logger.debug("Prepare to load Spring Security Resource Mapping...");

        Map<String, Collection<ConfigAttribute>> resourceMap = new LinkedHashMap<String, Collection<ConfigAttribute>>();

        //处理所有SecurityControllIgnore标记的权限为登录可访问, 数据集合来源为Struts2注入设置securityControllIgnoreUrls
        //for (String url : PostStrutsPrepareAndExecuteFilter.securityControlIgnoreUrls) {
        //    addURL2Role(resourceMap, url, Role.ROLE_ANONYMOUSLY_CODE);
        //}

        //设置角色关联权限
        Iterable<RoleR2Privilege> r2s = roleR2PrivilegeDao.findEnabledExcludeRole(Role.ROLE_ADMIN_CODE);
        for (RoleR2Privilege r2 : r2s) {
            if (StringUtils.isNotBlank(r2.getPrivilege().getUrl())) {
                addURL2Role(resourceMap, r2.getPrivilege().getUrl(), r2.getRole().getCode());
            }
        }

        Iterable<Privilege> privileges = privilegeDao.findByDisabled(false);
        for (Privilege privilege : privileges) {
            if (StringUtils.isNotBlank(privilege.getUrl())) {
                //ROLE_ADMIN角色默认赋予所有权限
                addURL2Role(resourceMap, privilege.getUrl(), Role.ROLE_ADMIN_CODE);
            }
        }

        

        //追加其他未受控权限
        addURL2Role(resourceMap, "/layout**", Role.ROLE_ANONYMOUSLY_CODE);

        //权限控制等级，可选值说明：
        //high=所有未配置对应权限的URL请求作为受保护资源进行控制，访问则抛出访问拒绝异常
        //low= 所有未配置对应权限的URL请求作为非保护资源宽松控制，只要登录用户都可以访问

        if (StringUtils.isNotBlank(authControlLevel) && authControlLevel.trim().equalsIgnoreCase("low")) {
            logger.warn("URL Security control running at level: Low");
            addURL2Role(resourceMap, "/**", Role.ROLE_ANONYMOUSLY_CODE);
        } else {
            logger.info("URL Security control running at level: High");
            addURL2Role(resourceMap, "/**", Role.ROLE_PROTECTED_CODE);
        }

        //ROLE_ADMIN角色默认赋予所有权限
        addURL2Role(resourceMap, "/**", Role.ROLE_ADMIN_CODE);

        if (logger.isDebugEnabled()) {
            logger.debug("Spring Security Resource Mapping:");
            for (Map.Entry<String, Collection<ConfigAttribute>> me : resourceMap.entrySet()) {
                logger.debug(" - {} : {}", me.getKey(), StringUtils.join(me.getValue(), ","));
            }
        }
        return resourceMap;
    }

    private void addURL2Role(Map<String, Collection<ConfigAttribute>> map, String url, String role) {
        for (String splitUrl : url.split("\n")) {
            if (StringUtils.isNotBlank(splitUrl)) {
                splitUrl = splitUrl.replace("?", "*");
                if (!splitUrl.endsWith("**")) {
                    splitUrl = splitUrl + "**";
                }
                Collection<ConfigAttribute> configAttributes = map.get(splitUrl);
                if (configAttributes == null) {
                    configAttributes = new ArrayList<ConfigAttribute>();
                    map.put(splitUrl, configAttributes);
                }
                configAttributes.add(new SecurityConfig(role));
            }
        }
    }

    @Override
    @CacheEvict(value = "SpringSecurityCache", allEntries = true)
    public Privilege save(Privilege entity) {
        return super.save(entity);
    }

    @Override
    @CacheEvict(value = "SpringSecurityCache", allEntries = true)
    public void delete(Privilege entity) {
        super.delete(entity);
    }

    @Override
    @CacheEvict(value = "SpringSecurityCache", allEntries = true)
    public List<Privilege> save(Iterable<Privilege> entities) {
        return super.save(entities);
    }

    @Override
    @CacheEvict(value = "SpringSecurityCache", allEntries = true)
    public void delete(Iterable<Privilege> entities) {
        super.delete(entities);
    }

    @CacheEvict(value = "SpringSecurityCache", allEntries = true)
    public void updateRelatedRoleR2s(String id, String[] roleIds) {
        updateRelatedR2s(id, roleIds, "roleR2Privileges", "role");
    }
}
