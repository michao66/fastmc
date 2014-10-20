package com.slsoft.btinfo.dao;

import org.springframework.stereotype.Repository;

import cn.fastmc.code.jpa.dao.SpringJpaDao;

import com.slsoft.btinfo.entify.BTXM;
@Repository
public interface BTXMDao extends SpringJpaDao<BTXM, String> {

}
