package com.slsoft.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fastmc.code.jpa.dao.SpringJpaDao;
import cn.fastmc.core.jpa.service.BaseService;

import com.slsoft.auth.dao.UserLogonLogDao;
import com.slsoft.auth.entify.UserLogonLog;

@Service
@Transactional
public class UserLogonLogService extends BaseService<UserLogonLog,String>{
    
    @Autowired
    private UserLogonLogDao userLogonLogDao;

    @Override
    protected SpringJpaDao<UserLogonLog, String> getEntityDao() {
        return userLogonLogDao;
    }
    
    public UserLogonLog findBySessionId(String httpSessionId){
        return userLogonLogDao.findByHttpSessionId(httpSessionId);
    }
}
