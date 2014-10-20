package com.slsoft.btinfo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.fastmc.code.jpa.dao.SpringJpaDao;

import com.slsoft.btinfo.entify.BTField;
import com.slsoft.btinfo.entify.BTXM;
@Repository
public interface BTFieldDao  extends SpringJpaDao<BTField, String> {
	List<BTField> findByProjectOrderByOrderNumAsc (String project);
	@Query("select distinct bt.btxm  from BTField bt  ")//JOIN bt.btxm xm
	List<BTXM> findDistinctBTXM( );
}
