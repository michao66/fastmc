package com.slsoft.btinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fastmc.code.jpa.dao.SpringJpaDao;
import cn.fastmc.core.ResultMessage;
import cn.fastmc.core.jpa.service.BaseService;
import cn.fastmc.core.jpa.specification.Specifications;

import com.slsoft.btinfo.dao.XZQYDao;
import com.slsoft.btinfo.entify.XZQY;
@Service
@Transactional
public class XZQYService  extends BaseService<XZQY, String> {

    @Autowired
    private XZQYDao xzqyDao;

  

    @Override
    protected SpringJpaDao<XZQY, String> getEntityDao() {
        return xzqyDao;
    }
    
    public ResultMessage saveXzqy(XZQY entity) {
   	 long count = xzqyDao.count(Specifications.get("id", entity.getId()));
   	 if(count>0){
   		 new ResultMessage(1,"地区编码已存在,请重新输入!");
   	 }
        super.save(entity);
        return new ResultMessage(1,"保存成功");
   }
    
    public ResultMessage updateXzqy(XZQY entity) {
    	super.update(entity);
    	return new ResultMessage(1,"保存成功");
    }

}
