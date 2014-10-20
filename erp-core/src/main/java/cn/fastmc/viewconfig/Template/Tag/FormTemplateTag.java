package cn.fastmc.viewconfig.Template.Tag;



import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.fastmc.core.DefaultCRUDService;
import cn.fastmc.viewconfig.components.Form;
import cn.fastmc.viewconfig.components.Page;
import cn.fastmc.viewconfig.components.ViewBase;

public class FormTemplateTag extends TemplateBodyTagSupport {

	private boolean updateState = false;
	private String entityId;

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public boolean isUpdateState() {
		return updateState;
	}

	public void setUpdateState(boolean updateState) {
		this.updateState = updateState;
		pageContext.getRequest().setAttribute(Form.FORM_UPDATESTATE,updateState);
	}

	@Override
	public ViewBase getView() {		
		Page page = getPageRepository().findPage(path);	
		Form form = page.getForm(id);
		return form;
	}
	
	public Form getForm(){
		return (Form)component;
	}
	 
    public int doStartTag() throws JspException {
    	component = getView();
    	String id = getRequest().getParameter("ID");
    	if(StringUtils.isNotEmpty(id) && pageContext.getRequest().getAttribute(Form.PAGECONTEXT_FORMVALUES)!=null){
    		setUpdateState(true);
    	}/*else if(StringUtils.isNotEmpty(id)){
    		WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
    		DefaultCRUDService defaultCRUD = (DefaultCRUDService)wc.getBean("defaultCRUD");
    		Object entityBean = defaultCRUD.getBeanById(getForm().getEntity(), id);
    		pageContext.getRequest().setAttribute(Form.PAGECONTEXT_FORMVALUES,entityBean);
    		setUpdateState(true)
    	}*/else if(StringUtils.isNotEmpty(entityId)){
    		Object entityBean = getRequest().getAttribute(entityId);
    		pageContext.getRequest().setAttribute(Form.PAGECONTEXT_FORMVALUES,entityBean);
    		setUpdateState(false);
    	}else{
    		setUpdateState(false);
    	}
    	pageContext.getRequest().setAttribute(Form.FORM_ACTION_URL,updateState?getForm().getUpdateAction():getForm().getSaveAction());
    	component.start(pageContext);
    	return EVAL_BODY_INCLUDE;
    } 
}
