package com.slsoft.auth.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.fastmc.code.jpa.dao.SpringJpaDao;

import com.slsoft.auth.entify.User;
import com.slsoft.auth.entify.UserR2Role;

@Repository
public interface UserR2RoleDao extends SpringJpaDao<UserR2Role, String> {

	@Query("select r2 from Role r, UserR2Role r2 where r=r2.role and r2.user=:user and r.disabled=false ")
	@QueryHints({ @QueryHint(name = org.hibernate.ejb.QueryHints.HINT_CACHEABLE, value = "true") })
	List<UserR2Role> findEnabledRolesForUser(@Param("user") User user);

	List<UserR2Role> findByRole_Id(String roleId);

	@Query("select r2 from User u, UserR2Role r2, Role r where r=r2.role and u=r2.user and r2.user=:user order by  r.code")
	List<UserR2Role> findByUser(@Param("user") User user);
}
