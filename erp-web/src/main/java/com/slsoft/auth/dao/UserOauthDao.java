package com.slsoft.auth.dao;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import cn.fastmc.code.jpa.dao.SpringJpaDao;

import com.slsoft.auth.entify.UserOauth;

@Repository
public interface UserOauthDao extends SpringJpaDao<UserOauth, String> {

	@QueryHints({ @QueryHint(name = org.hibernate.ejb.QueryHints.HINT_CACHEABLE, value = "true") })
	UserOauth findByUsername(String username);
}
