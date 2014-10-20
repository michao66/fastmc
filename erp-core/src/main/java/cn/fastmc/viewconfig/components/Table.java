package cn.fastmc.viewconfig.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.fastmc.core.pager;

public class Table extends ViewBase {

	public static final String TEMPLATE = "table.ftl"; 
	public static final String AJAX_TABLE_TEMPLATE = "ajaxTable.ftl";
	private List<TableHead> heads ;
	private pager pager;
	private String queryBeanId;
	
	private String typeClass;
    private String pagingSql;
	
	private String pagingCountSql;
   
	private boolean multiSelect;
    private boolean ajax = false;
    private String title ;
    
	private String style;
    private String dataOptions;
    
	public Table(){
		super();
		multiSelect = true;
		queryBeanId = "hqlQuery";//默认为HQL查询
		heads = new ArrayList<TableHead>();
	}
	public List<Collection<String>> rowIterator(){
		List<Collection<String>> rows = new ArrayList<Collection<String>>();
		for(Object bean: pager.getContent()){
			List<String> curRow = curRow(bean);
			rows.add(curRow);
		}
		return rows;
	}
	
	public List<String> curRow(Object bean){
		List<String> row = new ArrayList<String>();
		for(TableHead head : getHeads()){
			Object obj = StringUtils.isNotEmpty(head.getField())?findValue(bean,head.getField()):bean;
			HtmlRender render = HtmlRenderFactory.getRender(head.getType());
			row.add(render.render(head, obj, head.getFormat()));
		}
		return row;
	}
	
	protected void evaluateParams(PageContext context) {
		super.evaluateParams(context);
		List<Collection<String>> rows = rowIterator();
		addParameter("rowIterator",rows);
	}
	
	public String getPagingSql() {
		return pagingSql;
	}
	public void setPagingSql(String pagingSql) {
		this.pagingSql = pagingSql;
	}
	
	public List<TableHead> getHeads() {
		return heads;
	}
	public void setHeads(List<TableHead> heads) {
		this.heads = heads;
	}
	public void addHead(TableHead head){
		heads.add(head);
	}
	@Override
	protected String getDefaultTemplate() {
		if(ajax){
			return 	AJAX_TABLE_TEMPLATE;//返回AJAX方式分页方式模板
		}
		return TEMPLATE;// 普通方式分页
	}
	public pager getPager() {
		return pager;
	}

	public void setPager(pager pager) {
		this.pager = pager;
	}
	
	
	
	public String getTypeClass() {
		return typeClass;
	}

	public void setTypeClass(String typeClass) {
		this.typeClass = typeClass;
	}
	
   
	public String getPagingCountSql() {
		return pagingCountSql;
	}

	public void setPagingCountSql(String pagingCountSql) {
		this.pagingCountSql = pagingCountSql;
	}
	
	public boolean isMultiSelect() {
		return multiSelect;
	}
	public void setMultiSelect(boolean multiSelect) {
		this.multiSelect = multiSelect;
	}
	
	public boolean isAjax() {
		return ajax;
	}
	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getDataOptions() {
		return dataOptions;
	}
	public void setDataOptions(String dataOptions) {
		this.dataOptions = dataOptions;
	}
	public String getQueryBeanId() {
		return queryBeanId;
	}
	public void setQueryBeanId(String queryBeanId) {
		this.queryBeanId = queryBeanId;
	}
    
  

}
