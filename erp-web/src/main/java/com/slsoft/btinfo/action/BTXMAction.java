package com.slsoft.btinfo.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.fastmc.core.jpa.service.BaseService;

import com.slsoft.btinfo.entify.BTXM;
import com.slsoft.btinfo.service.BTXMService;
import com.slsoft.core.action.BaseController;

@Controller
@RequestMapping("btinfo/btxm")
public class BTXMAction extends BaseController<BTXM, String> {

	@Autowired
	private BTXMService btxmService;

	public BTXMAction() {
		super();
		this.pageId = "btinfo";
		this.formId = "btxmForm";
	}

	@Override
	protected BaseService<BTXM, String> getEntityService() {
		// TODO Auto-generated method stub
		return this.btxmService;
	}

	@RequestMapping("index")
	public String index() {
		return "/admin/btxm/list";
	}


}
