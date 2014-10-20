package com.slsoft.btinfo.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.fastmc.core.jpa.service.BaseService;
import cn.fastmc.excelTools.ExcelUtils;
import cn.fastmc.excelTools.JsGridReportBase;
import cn.fastmc.excelTools.TableData;

import com.slsoft.btinfo.entify.BTField;
import com.slsoft.btinfo.service.BtFieldService;
import com.slsoft.core.action.BaseController;

@Controller
@RequestMapping("btinfo/bttemplete")
public class BtTempleteAction extends BaseController<BTField, String> {
	
    private static final Log logger = LogFactory.getLog(BtTempleteAction.class);

	@Autowired
	private BtFieldService btFieldService;
	
	@Override
	protected BaseService<BTField, String> getEntityService() {
		return this.btFieldService;
	}
	@RequestMapping("index")
	public String index() {
		return "/admin/bttemplete/list";
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping("expExcelTemp")
	public void expExcelTemp(HttpServletRequest request,HttpServletResponse response){
		try{
			String projectId = request.getParameter("projectId");
			String xzqy = request.getParameter("xzqy");
			String temp1 = request.getParameter("rowNum");
			int rowNum = NumberUtils.toInt(temp1, 20);
			Assert.notNull(projectId, "项目编号不能为空");
			List<BTField> list = btFieldService.findFieldByProject(projectId);
			String title = list.get(0).getProjectText();
			String[] hearders = new String[list.size()];// 表头数组
			String[] fields = new String[list.size()];
			for(int i = 0 ;i<list.size();i++){
				BTField field = list.get(i);
				hearders[i] = field.getLable();
				fields[i] = field.getFildId();
			}
			List data = new ArrayList();
			for(int i = 0 ;i<=rowNum;i++){
				Map row = new HashMap();
				for(String field : fields){
				
					if("departid".equals(field)){
						row.put(field,xzqy);
					}else if("project".equals(field)){
						row.put(field, projectId);
					}else{
						row.put(field, "");
					}
				}
				data.add(row);
			}
			TableData td = ExcelUtils.createTableData(data, ExcelUtils.createTableHeader(hearders), fields);
			JsGridReportBase report = new JsGridReportBase(request, response);
			report.exportToExcel(title, td);
		}catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		
	}
	
	
}
