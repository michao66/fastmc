package com.slsoft.btinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fastmc.code.jpa.dao.SpringJpaDao;
import cn.fastmc.core.jpa.service.BaseService;
import cn.fastmc.viewconfig.PageRepository;
import cn.fastmc.viewconfig.components.Combobox;
import cn.fastmc.viewconfig.components.Form;
import cn.fastmc.viewconfig.components.FormItem;
import cn.fastmc.viewconfig.components.Page;
import cn.fastmc.viewconfig.components.Validator;

import com.slsoft.btinfo.dao.BTXXDao;
import com.slsoft.btinfo.entify.BTField;
import com.slsoft.btinfo.entify.BTXX;

@Service
@Transactional
public class BTXXService extends BaseService<BTXX, String>{
	    @Autowired
	    private BtFieldService btFieldService;
	    @Autowired
	    private BTXMService  btxmService ;
	    @Autowired
	    private BTXXDao btxxDao;
	    @Autowired
	    PageRepository pageRepository;

	    @Override
	    protected SpringJpaDao<BTXX, String> getEntityDao() {
	        return btxxDao;
	    }
	    
	    public void batchInsertBtxx(List<BTXX> bts){
	    	btxxDao.batchInsert(bts);
	    }
	        
	public void createForm(String projectId) {
		List<BTField> bts = btFieldService.findFieldByProject(projectId);
		if (!pageRepository.exists("dynamicPage",projectId)) {
			Page page = new Page();
			page.setId("dynamicPage");
			Form form = new Form();
			form.setColumn(2);
			form.setEntity("com.slsoft.btinfo.entify.BTXX");
			form.setId(projectId);
			form.setSaveAction("btinfo/btxx/doCreate.action");
			for (BTField field : bts) {
				FormItem item = null;
				if("project".equals(field.getFildId())){
					item  = new Combobox();
					item.setType("combobox");
					item.addParameter("loader","configBtXMload");
				}else{
					 item = new FormItem();
					 item.setType(field.getType());	
				}
				if(!"none".equals(field.getAttribute())){
			           Validator validator = new Validator();
			           validator.setId(field.getFildId());
			           validator.handleValidatorValue(field.getAttribute());
			           form.addValidators(validator);
				}
				item.setId(field.getFildId());
				item.setLabel(field.getLable());
				form.addFormItems(item);
			}
			FormItem id = new FormItem();
			id.setType("hidden");
			id.setId("id");
			form.addFormItems(id);
			page.addForm(form);
			pageRepository.addPage(page);
		}

	}
	    
	    
}
