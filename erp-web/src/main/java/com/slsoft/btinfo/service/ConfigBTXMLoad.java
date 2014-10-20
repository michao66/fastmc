package com.slsoft.btinfo.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fastmc.viewconfig.components.FormItem;
import cn.fastmc.viewconfig.components.ListDataLoad;

import com.slsoft.btinfo.entify.BTXM;
@Service("configBtXMload")
@Transactional
public class ConfigBTXMLoad implements ListDataLoad {
	@Autowired
	private BtFieldService btFieldService;
	
	@Override
	@Transactional(readOnly = true)
	public Map<String, String> load(FormItem item, PageContext context) {
		Map<String, String> r = new LinkedHashMap<String, String>();
		List<BTXM> bts= btFieldService.getConfigBTXM();
		for(BTXM bt:bts){
			r.put(bt.getId(), bt.getName());
		}
		return r;
	}

}
