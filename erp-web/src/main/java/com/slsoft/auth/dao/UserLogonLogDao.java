package com.slsoft.auth.dao;

import org.springframework.stereotype.Repository;

import cn.fastmc.code.jpa.dao.SpringJpaDao;

import com.slsoft.auth.entify.UserLogonLog;

@Repository
public interface UserLogonLogDao extends SpringJpaDao<UserLogonLog, String> {

    UserLogonLog findByHttpSessionId(String httpSessionId);
}