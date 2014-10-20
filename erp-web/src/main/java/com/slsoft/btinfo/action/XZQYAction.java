package com.slsoft.btinfo.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.fastmc.core.jpa.service.BaseService;
import cn.fastmc.core.pagination.GroupPropertyFilter;
import cn.fastmc.core.pagination.PropertyFilter;
import cn.fastmc.core.pagination.PropertyFilter.MatchType;
import cn.fastmc.core.utils.ResponseUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.slsoft.btinfo.entify.XZQY;
import com.slsoft.btinfo.service.XZQYService;
import com.slsoft.core.action.BaseController;
@Controller
@RequestMapping("btinfo/xzqy")
public class XZQYAction extends BaseController<XZQY, String> {
	
	@Autowired
	private XZQYService xzqyService;

	public XZQYAction() {
		super();
		this.pageId = "btinfo";
		this.formId = "xzqyForm";
	}

	@Override
	protected BaseService<XZQY, String> getEntityService() {
		// TODO Auto-generated method stub
		return this.xzqyService;
	}

	@RequestMapping("index")
	public String index() {
		return "/admin/xzqy/list";
	}

	
	@RequestMapping("autocomplete")
	public void autocomplete(HttpServletRequest request,
			HttpServletResponse response){
		
		String q_str = request.getParameter("q_term");
		Sort sort = new Sort("id");
		
		GroupPropertyFilter groupPropertyFilter = GroupPropertyFilter.buildDefaultOrGroupFilter();
        groupPropertyFilter.append(new PropertyFilter(MatchType.BW, "name",q_str));
        groupPropertyFilter.append(new PropertyFilter(MatchType.BW, "id",q_str));
        groupPropertyFilter.append(new PropertyFilter(MatchType.NC, "pinyin",q_str));  
		List<XZQY> data = xzqyService.findByFilters(groupPropertyFilter, sort, 10);
		JSONArray jsonData = new JSONArray();
		for(XZQY d : data){
			JSONObject rowData = new JSONObject();
			rowData.put("id", d.getId());
			rowData.put("text", d.getId()+"_"+d.getName());
			jsonData.add(rowData);
		}
		ResponseUtils.renderJson(response,jsonData.toString()); 
		
	}

}
