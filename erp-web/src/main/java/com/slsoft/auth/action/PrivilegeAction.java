package com.slsoft.auth.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.fastmc.core.jpa.service.BaseService;

import com.slsoft.auth.entify.Privilege;
import com.slsoft.auth.service.PrivilegeService;
import com.slsoft.core.action.BaseController;
@Controller
@RequestMapping("auth/privilege")
public class PrivilegeAction extends BaseController<Privilege, String> {
	@Autowired
	private PrivilegeService privilegeService;

	public PrivilegeAction() {
		super();
		this.pageId = "system";
		this.formId = "privilegeForm";
	}

	@Override
	protected BaseService<Privilege, String> getEntityService() {
		// TODO Auto-generated method stub
		return this.privilegeService;
	}
	
	@RequestMapping("index")
	public String index() {
		return "/admin/privilege/list";
	}
	
	

}
