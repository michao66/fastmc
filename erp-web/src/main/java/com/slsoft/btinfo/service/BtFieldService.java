package com.slsoft.btinfo.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fastmc.code.jpa.dao.SpringJpaDao;
import cn.fastmc.core.jpa.service.BaseService;

import com.slsoft.btinfo.dao.BTFieldDao;
import com.slsoft.btinfo.dao.BTXMDao;
import com.slsoft.btinfo.entify.BTField;
import com.slsoft.btinfo.entify.BTXM;

@Service
@Transactional
public class BtFieldService extends BaseService<BTField, String> {


    @Autowired
    private BTFieldDao btFieldDao;
    @Autowired
    private BTXMDao  btxmDao ;

    @Override
    protected SpringJpaDao<BTField, String> getEntityDao() {
        return this.btFieldDao;
    }
    @Override
    protected void preInsert(BTField entity){
    	getBTXM(entity);
    	
    }
    @Override
    protected void preUpdate(BTField entity){
    	getBTXM(entity);
    	
    }
	private void getBTXM(BTField entity) {
		if(StringUtils.isNotEmpty(entity.getProject())){
    		 BTXM btxm = btxmDao.findOne(entity.getProject());
    		 entity.setBtxm(btxm);
    	}
	}
	
	public  List<BTField> findFieldByProject(String projectId){
		return btFieldDao.findByProjectOrderByOrderNumAsc(projectId);
	}
	
	public List<String[]> getQueryFieldByProject(String projectId){
		List<BTField> btfields = btFieldDao.findByProjectOrderByOrderNumAsc(projectId);
		List<String[]>  returns = new ArrayList<String[]>(2);
		//String[][] field =  new String[btfields.size()][2];
		String[] field = new String[btfields.size()];
		String[] display = new String[btfields.size()];
		for(int i=0;i<btfields.size();i++){
			field[i] = btfields.get(i).getFildId();
			display[i] = btfields.get(i).getLable();
		}
		returns.add(field);
		returns.add(display);
		return returns;
	}
	
	public List<BTXM> getConfigBTXM(){
		return btFieldDao.findDistinctBTXM();
	}

}
