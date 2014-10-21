package com.slsoft.btinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fastmc.code.jpa.dao.SpringJpaDao;
import cn.fastmc.core.ServiceException;
import cn.fastmc.core.jpa.service.BaseService;
import cn.fastmc.core.jpa.specification.Specifications;

import com.slsoft.btinfo.dao.BTXMDao;
import com.slsoft.btinfo.entify.BTXM;

@Service
@Transactional
public class BTXMService extends BaseService<BTXM, String> {


    @Autowired
    private BTXMDao btxmDao;

  

    @Override
    protected SpringJpaDao<BTXM, String> getEntityDao() {
        return btxmDao;
    }
    protected void preInsert(BTXM entity){
    	long count = btxmDao.count(Specifications.get("id", entity.getId()));
	   	if(count>0){
	   	   throw new ServiceException("项目编号已存在,请重新输入!");
	   	}
    }
    
    
    public List<BTXM> findAllBtxm(){
    	return btxmDao.findAll();
    }
    
    
   
   
}
