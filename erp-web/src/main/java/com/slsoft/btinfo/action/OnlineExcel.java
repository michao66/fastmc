package com.slsoft.btinfo.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.fastmc.core.jpa.service.BaseService;

import com.slsoft.btinfo.entify.BTXX;
import com.slsoft.btinfo.service.BTXXService;
import com.slsoft.core.action.BaseController;
@Controller
@RequestMapping("btinfo/onlineExcel")
public class OnlineExcel extends BaseController<BTXX, String>{
	@Autowired
	private BTXXService btxxService;
	@Override
	protected BaseService<BTXX, String> getEntityService() {
		// TODO Auto-generated method stub
		return this.btxxService;
	}
	
	@RequestMapping("index")
	public String index() {
		return "/admin/onlineExcel/list";
	}

}
