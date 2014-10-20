package com.slsoft.btinfo.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.fastmc.core.jpa.service.BaseService;

import com.slsoft.btinfo.entify.BTField;
import com.slsoft.btinfo.service.BtFieldService;
import com.slsoft.core.action.BaseController;

@Controller
@RequestMapping("btinfo/btfield")
public class BtFieldAction extends BaseController<BTField, String> {
	@Autowired
	private BtFieldService btFieldService;

	public BtFieldAction() {
		super();
		this.pageId = "btinfo";
		this.formId = "btfieldForm";
	}

	@Override
	protected BaseService<BTField, String> getEntityService() {
		// TODO Auto-generated method stub
		return this.btFieldService;
	}

	@RequestMapping("index")
	public String index() {
		return "/admin/btfield/list";
	}

	
}
