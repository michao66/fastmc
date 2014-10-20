package cn.fastmc.viewconfig.Template.Tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.beans.BeansException;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.fastmc.viewconfig.PageRepository;
import cn.fastmc.viewconfig.components.ViewBase;

public abstract class TemplateBodyTagSupport extends BodyTagSupport {
	protected String id;
	protected String path;
	protected ViewBase component;
	 
	public TemplateBodyTagSupport(){
		super();
	}
	
	protected String getWebPath(){
		String path = getRequest().getContextPath();
		String basePath = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort()+path+"/";
		return basePath;
	}
	
	public ViewBase getComponent() {
		return component;
	}

	public abstract ViewBase getView();
	
	public HttpServletRequest getRequest(){
		return (HttpServletRequest) pageContext.getRequest();
	}
	
	public HttpServletResponse getResponse(){
		return (HttpServletResponse) pageContext.getResponse();
	}
	
    public int doEndTag() throws JspException {
    	component.end(pageContext);
        return EVAL_PAGE;
    }
    protected PageRepository getPageRepository(){
    	 WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
    	 PageRepository	pageRepository = wc.getBean(PageRepository.class);
    	 return pageRepository;
    }
    protected  Object getBean(String name) throws BeansException {
		Assert.hasText(name);
		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
		return wc.getBean(name);
	}
   
    
    public int doStartTag() throws JspException {
    	evaluateExpressions();
    	return EVAL_BODY_INCLUDE;
    }
    
    public String getId() {
		return id;
	}
    protected void evaluateExpressions()
            throws JspException {
    	 String string = null;
         if ((string =
                 EvalHelper.evalString("id", getId(), this,
                     pageContext)) != null) {
        	 setId(string);
         }
         
         if ((string =
                 EvalHelper.evalString("path", getPath(), this,
                     pageContext)) != null) {
        	 setPath(string);
         }
    }

	public void setId(String id) {
		  
		this.id =  id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
