package cn.fastmc.core.taglib;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.fastmc.core.Pageable;
import cn.fastmc.core.pager;
import cn.fastmc.core.utils.FunctionUtils;
import cn.fastmc.core.utils.RequestUtils;
import cn.fastmc.sqlconfig.query.BaseQuery;
import cn.fastmc.viewconfig.Template.Tag.EvalHelper;





public class InvokeQueryTag extends TagSupport {
	private static final Log logger = LogFactory.getLog(InvokeQueryTag.class);
	private String id;


	private String sql;// 配置SQL的唯一 ID
	private String countSql;//配置查詢總行數SQL的唯一 ID 只用于分頁情況
	private String strparam;//查詢參數，多個參數之間用&隔開
	private String type = "hql";//執行查詢類別，hsq,jdbc兩種
	private int pageSize = 0;//用于分頁情況，第次查詢多少行
		public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getStrparam() {
		return strparam;
	}
	public void setStrparam(String strparam) {
		this.strparam = strparam;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int doStartTag() throws JspException {
		try {
			evaluateExpressions();
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
			BaseQuery baseQuery = (BaseQuery) wc.getBean("hql".equals(type)?"hqlQuery":"jdbcQuery");
			Map<String, String> param  = RequestUtils.getQueryParams(request);
			if (StringUtils.isNotBlank(strparam)) {
				 param=FunctionUtils.getQueryMap(strparam);
			}
			List<?> datas = null;
           if(pageSize>0){//分頁情況
   			    String strstart = param.get("pageNumber") == null?request.getParameter("pageNumber"): param.get("pageNumber");
   			    int pageNumber = NumberUtils.toInt(strstart, 0);
        	    long total = 0;
        	    Pageable pageable = new Pageable(pageNumber,pageSize);  
   			    pageable.setSearchParam(param);
	        	if(StringUtils.isNotEmpty(countSql)){
	        		total = baseQuery.findCount(countSql.trim(), param);
	   			}
				datas = baseQuery.findPages(sql.trim(), param, (pageable.getPageNumber() - 1) * pageable.getPageSize(), pageable.getPageSize());
				request.setAttribute("page", new pager(datas, total, pageable));
			}else{//只取List的情況
				datas = baseQuery.findList(sql.trim(), param);
				request.setAttribute(id, datas);
			}
			 
			 return (EVAL_BODY_INCLUDE);
		}catch(Exception ex) {
			logger.error(ex);
			throw new JspException(ex);
		}
	}
	 private void evaluateExpressions() throws JspException{
		 String string = null;
         if ((string =
                 EvalHelper.evalString("sql", getSql(), this, pageContext)) != null) {
        	 setSql(string);
         }
         if ((string =
                 EvalHelper.evalString("strparam", getStrparam(), this,pageContext)) != null) {
        	     setStrparam(string);
         }
         if ((string =
                 EvalHelper.evalString("type", getType(), this, pageContext)) != null) {
        	     setType(string);
         }
         
	 }
	public int doEndTag() throws JspException {
		return (EVAL_PAGE);
	}
	public String getCountSql() {
		return countSql;
	}
	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
   
	
	
}

