package com.slsoft.btinfo.action;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.fastmc.core.ServiceException;
import cn.fastmc.core.jpa.service.BaseService;
import cn.fastmc.core.utils.FunctionUtils;
import cn.fastmc.core.utils.ResponseUtils;
import cn.fastmc.web.spring.mvc.json.annotation.RequestJsonParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.slsoft.btinfo.entify.BTField;
import com.slsoft.btinfo.entify.BTXX;
import com.slsoft.btinfo.service.BTXXService;
import com.slsoft.btinfo.service.BtFieldService;
import com.slsoft.core.action.BaseController;
@Controller
@RequestMapping("btinfo/btxx")
public class BTXXAction extends BaseController<BTXX, String>{
    private static final Log logger = LogFactory.getLog(BTXXAction.class);
	@Autowired
	private BTXXService btxxService;
	@Autowired
	private BtFieldService btFieldService;
	

	@Override
	protected BaseService<BTXX, String> getEntityService() {
		// TODO Auto-generated method stub
		return this.btxxService;
	}
	
	@RequestMapping("index")
	public String index() {
		return "/admin/btxx/list";
	}
	@RequestMapping("createBtxx")
	public ModelAndView createBtxx(String projectId) {
		btxxService.createForm(projectId);
		super.setFormId(projectId);
		super.setPageId("dynamicPage");
		return buildDefaultInputView(null);
	}
	@RequestMapping("eidtBtxx")
	public ModelAndView eidtBtxx(String ID,String projectId) {
		BTXX bindingEntity = null;
    	String id = getId(ID);
    	if(null != id){
    		 bindingEntity = getEntityService().findOne(id);
    	}
		btxxService.createForm(projectId);
		super.setFormId(projectId);
		super.setPageId("dynamicPage");
		return buildDefaultInputView(bindingEntity);
	}
	
	@Override
	public void findByNativePage(@RequestParam Map<String,String> queryparam,HttpServletRequest request,HttpServletResponse response) {
    	List<String[]> headers = btFieldService.getQueryFieldByProject(queryparam.get("projectId"));
    	String[] fieldIds = headers.get(0);
    	for(String field : fieldIds){
    		queryparam.put(field, field);
    	}
		super.findByNativePage(queryparam, request, response);
	}
	
	@RequestMapping("getTableHeader")
	public void getTableHeader(HttpServletRequest request,HttpServletResponse response) {
		String projectId = request.getParameter("projectId");
		List<String[]> headers = btFieldService.getQueryFieldByProject(projectId);
		JSONObject jsonData = new JSONObject();
		if(headers.size()>1){
			String[] field = headers.get(0);
			String[] title = headers.get(1);
			
			JSONArray rowData = new JSONArray();
			for(int i=0;i<field.length;i++){
				JSONObject data = new JSONObject();
				data.put("field", field[i]);
				data.put("title", title[i]);
				data.put("align", "center");
				data.put("width", 100);
				rowData.add(data);
				
			}
			JSONObject data = new JSONObject();
			data.put("field", "id");
			data.put("hidden", true);
			rowData.add(data);
			jsonData.put("columns", rowData);
		}else{
			jsonData.put("columns", "[]");
		}
		ResponseUtils.renderJson(response,jsonData.toString());
	}
	
	@RequestMapping("imp")
	public ModelAndView imp(){
		super.setFormId("btxxImpForm");
		super.setPageId("btinfo");
		return buildDefaultInputView(null);
	}
	@RequestMapping("doimp")
	public void doImp(@RequestParam("myUpload")MultipartFile myUpload,HttpServletRequest request,HttpServletResponse response){
		 if (!FilenameUtils.isExtension(myUpload.getOriginalFilename(), "xls")){
	    	   ResponseUtils.ajaxJsonWarnMessage(response, "上传文件格式不正确,请重新选择!");
	     }
		  POIFSFileSystem fs  = null;
		  HSSFWorkbook wb  = null;
		  HSSFSheet sheet  = null;
		  HSSFRow row  = null;
		  File file  = null;
		  InputStream inp = null;
		  String projectId = request.getParameter("projectId");
		  List<BTField> fidlds = btFieldService.findFieldByProject(projectId);
		 try{
			 String fileName =  UUID.randomUUID() + "." + FilenameUtils.getExtension(myUpload.getOriginalFilename());
			 String root = this.servletContext.getRealPath("upload");
			 FunctionUtils.mkDirectory(root);
			 FunctionUtils.upload(fileName, root, myUpload.getInputStream());
			 String readPath = root+"/"+fileName;
			  file = new File(readPath);
			 List<BTXX> data = new ArrayList<BTXX>();
		      if (file.exists()) {
		    	 inp = new FileInputStream(file);
		    	 fs = new POIFSFileSystem(inp);
		         wb = new HSSFWorkbook(fs);
		         sheet = wb.getSheetAt(0);
		         // 得到总行数
		         int rowNum = sheet.getLastRowNum();
		         if(rowNum>5000)rowNum=5000;
		         row = sheet.getRow(0);
		         int colNum = row.getPhysicalNumberOfCells();
		         // 正文内容应该从第二行开始,第一行为表头的标题
		         for (int i = 2; i <= rowNum; i++) {
		             row = sheet.getRow(i);
		             BTXX bt = new BTXX(); 
		             int j = 0;
		             while (j < colNum) {
		            	 if(j<=fidlds.size()){
		            		 HSSFCell cell = row.getCell(j);
		            		 if(cell != null){
		            			 getCellValue2Bean(cell,bt,fidlds.get(j));
		            		 }
		            	 }
		            	 j++;
		             }
		             data.add(bt);
		         }
		         btxxService.batchInsertBtxx(data);
		         ResponseUtils.renderAjaxJsonSuccessMessage(response,"操作成功");
		      }
		 }catch(Exception ex){
			 logger.error(ex);
			 throw new ServiceException(ex);
		 }finally{
			 try{
				 if(inp!=null)inp.close(); 
			 }catch(Exception ex){
				 
			 }
		 }
			 
		 
	}
	
	private void getCellValue2Bean(HSSFCell cell, BTXX bt, BTField btField) {
		try {
			String strCell = getStringCellValue(cell);     
			PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(bt, btField.getFildId());

			if (String.class.isAssignableFrom(pd.getPropertyType())) {
				BeanUtils.setProperty(bt, btField.getFildId(),strCell);
			} else if (double.class.isAssignableFrom(pd.getPropertyType())) {

				BeanUtils.setProperty(bt, btField.getFildId(),NumberUtils.toDouble(strCell,0));

			} else {
				BeanUtils.setProperty(bt, btField.getFildId(),strCell);
			}

		} catch (Exception ex) {
			logger.error(ex);
			throw new ServiceException(ex);
		}

	}
	
	 /**
     * 获取单元格数据内容为字符串类型的数据
     * 
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
	private String getStringCellValue(HSSFCell cell) {
		String strCell = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
		    DecimalFormat df = new DecimalFormat("#");
			strCell = df.format(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		
		return strCell;
	}
	
	//保存在线编辑数据
	@RequestMapping(value="onlineExceSave", method = RequestMethod.POST)
	public void onlineExceSave(@RequestJsonParam(value = "data") List<BTXX> data,HttpServletRequest request,HttpServletResponse response){
		if(CollectionUtils.isNotEmpty(data)){
			 btxxService.batchInsertBtxx(data);
		}
		 ResponseUtils.renderAjaxJsonSuccessMessage(response,"操作成功");
	}
	
	
	
	
	
}
