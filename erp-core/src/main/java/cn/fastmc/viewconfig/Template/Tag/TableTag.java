package cn.fastmc.viewconfig.Template.Tag;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.fastmc.core.Order.Direction;
import cn.fastmc.core.Pageable;
import cn.fastmc.core.pager;
import cn.fastmc.core.annotation.EntityAutoCode;
import cn.fastmc.core.annotation.MetaData;
import cn.fastmc.core.utils.RequestUtils;
import cn.fastmc.sqlconfig.query.Iquery;
import cn.fastmc.viewconfig.Template.Tag.EvalHelper;
import cn.fastmc.viewconfig.Template.Tag.TemplateBodyTagSupport;
import cn.fastmc.viewconfig.components.Page;
import cn.fastmc.viewconfig.components.Table;
import cn.fastmc.viewconfig.components.TableHead;
import cn.fastmc.viewconfig.components.ViewBase;

public class TableTag extends TemplateBodyTagSupport{
	private static final Log LOG = LogFactory.getLog(TableTag.class);
	private String entityFullName;
	private int pageSize = 20;
	private String queryBeanId;
	private String pagingSql;
	private String pagingCountSql;
	private boolean multiSelect = true;
	private String cssClass;

	

	public String getEntityFullName() {
		return entityFullName;
	}

	public void setEntityFullName(String entityFullName) {
		this.entityFullName = entityFullName;
	}

	public int doEndTag() throws JspException {
		
    	component.end(pageContext);  
		return EVAL_PAGE;
	}
	
    @Override
	public ViewBase getView() {
		Page page = getPageRepository().findPage(path);
		if(page == null ){
			component = createTable();
			page = new Page();
			page.setId(path);
			component.setId(id);
			page.addTable((Table)component);
			getPageRepository().addPage(page);
		}else{
			component = page.getTable(id);
			if(component == null){
				component = createTable();
				page.addTable((Table)component);
			}
		}
		return component;
	}
    
    @Override
    protected void evaluateExpressions()throws JspException {
    	super.evaluateExpressions();
    	String string = null;
        if ((string =
                EvalHelper.evalString("pagingSql", getPagingSql(), this,
                    pageContext)) != null) {
        	setPagingSql(string);
        }
        if ((string =
                EvalHelper.evalString("pagingCountSql", getPagingCountSql(), this,
                    pageContext)) != null) {
        	setPagingCountSql(string);
        }
        if ((string =
                EvalHelper.evalString("entityFullName", getEntityFullName(), this,
                    pageContext)) != null) {
        	setEntityFullName(string);
        }
        if ((string =
                EvalHelper.evalString("queryBeanId", getQueryBeanId(), this,
                    pageContext)) != null) {
        	setQueryBeanId(string);
        }
        
       
    }
	private Table createTable() {
		Table table = new Table();
		table.setId(id);
		table.setStyle(cssClass);
		table.setPagingCountSql(pagingCountSql);
		table.setPagingSql(pagingSql);
		if(StringUtils.isNotEmpty(queryBeanId))table.setQueryBeanId(queryBeanId);
		
		try {
			Class entityClass = Class.forName(entityFullName);
			MetaData classEntityComment = (MetaData) entityClass.getAnnotation(MetaData.class);
			if (classEntityComment != null) {
				table.setTitle(classEntityComment.value());
			}
			Set<Field> fields = new HashSet<Field>();
			Field[] curfields = entityClass.getDeclaredFields();
			for (Field field : curfields) {
				fields.add(field);
			}
			for (Field field : fields) {
				if ("id".equals(field.getName())) {
					TableHead tableHead = new TableHead();
					tableHead.setTitle("id");
					tableHead.setHidden(false);
					tableHead.setField(field.getName());
					tableHead.setOrder(0);
					if(multiSelect)tableHead.setType("check");
					table.addHead(tableHead);
                }
				MetaData entityMetaData = field.getAnnotation(MetaData.class);				
				EntityAutoCode entityAutoCode = field.getAnnotation(EntityAutoCode.class);
				if (entityAutoCode != null && entityMetaData != null) {
					Class fieldType = field.getType();
					TableHead tableHead = new TableHead();
					tableHead.setTitle(entityMetaData.value());
					tableHead.setField(field.getName());
					tableHead.setOrder(entityAutoCode.order());
					tableHead.setHidden(entityAutoCode.listShow()||entityAutoCode.listHidden());
					if(fieldType.isEnum()) {
						 tableHead.setListAlign("center");
						 tableHead.setWidth(80);
					} else if (fieldType == Boolean.class) {
						 tableHead.setListAlign("center");
						 tableHead.setWidth(80);
					} else if (Number.class.isAssignableFrom(fieldType)) {
						 tableHead.setType("money");
						 tableHead.setListAlign("right");
						 tableHead.setWidth(80);
					}else if (fieldType == Date.class) {
						 tableHead.setType("date");
						 tableHead.setFormat(entityAutoCode.pattern());
						 tableHead.setWidth(120);
						 tableHead.setListAlign("center");
					}else{
						tableHead.setWidth(entityAutoCode.width());
					}
					table.addHead(tableHead);
				}
			}			
		} catch (Exception ex) {
			LOG.error(ex);
		}
		if(CollectionUtils.isNotEmpty(table.getHeads()))Collections.sort(table.getHeads());
		return table;
	}

	 @SuppressWarnings({"rawtypes", "unchecked" })
	public int doStartTag() throws JspException {
		 component = getView();
		 Table table= (Table)component;
		 Map<String,String> paramMap = RequestUtils.getQueryParams(getRequest());
		 String strstart = paramMap.get("pageNumber");
		 
		 String orderProperty = paramMap.get("orderProperty");
		 String orderDirection = StringUtils.defaultIfEmpty(paramMap.get("orderDirection"),"asc");
		 
		 int pageNumber = NumberUtils.toInt(strstart, 0);
 	     long total = 0;
 	     pageSize  = NumberUtils.toInt(paramMap.get("pageSize"), 10);
 	     Pageable pageable = new Pageable(pageNumber,pageSize);  
 	     pageable.setOrderProperty(orderProperty);
 	     pageable.setOrderDirection(Direction.valueOf(orderDirection));
		 pageable.setSearchParam(paramMap);
		 if(StringUtils.isNotEmpty(table.getPagingCountSql()) && StringUtils.isNotEmpty(table.getPagingSql())){
			 Iquery query = (Iquery)getBean(table.getQueryBeanId());
			 List  datas = query.findPages(table.getPagingSql().trim(), paramMap, (pageable.getPageNumber() - 1) * pageable.getPageSize(), pageable.getPageSize());
			 total = query.findCount(table.getPagingCountSql(), paramMap);
			 table.setPager(new pager(datas, total, pageable) );
		 }
		 component.start(pageContext);
		return SKIP_BODY;
	}
	 
	 public String getPagingSql() {
			return pagingSql;
		}

		public void setPagingSql(String pagingSql) {
			this.pagingSql = pagingSql;
		}

		public String getPagingCountSql() {
			return pagingCountSql;
		}

		public void setPagingCountSql(String pagingCountSql) {
			this.pagingCountSql = pagingCountSql;
		}

		
	    public String getQueryBeanId() {
			return queryBeanId;
		}

		public void setQueryBeanId(String queryBeanId) {
			this.queryBeanId = queryBeanId;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}
		
		public boolean isMultiSelect() {
			return multiSelect;
		}

		public void setMultiSelect(boolean multiSelect) {
			this.multiSelect = multiSelect;
		}
		
		public String getCssClass() {
			return cssClass;
		}

		public void setCssClass(String cssClass) {
			this.cssClass = cssClass;
		}
}
