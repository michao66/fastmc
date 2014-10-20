package cn.fastmc.menu.taglib;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.fastmc.menu.MenuComponent;
import cn.fastmc.menu.MenuRepository;
import cn.fastmc.viewconfig.Template.Tag.EvalHelper;

public class DisplayMenuTag extends TagSupport{
	 protected final static Log log = LogFactory.getLog(DisplayMenuTag.class);
	 protected String name;
	 protected String id;
	 public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	 private void evaluateExpressions()throws JspException {
	    	 String string = null;
	         if ((string =EvalHelper.evalString("id", getId(), this, pageContext)) != null) {
	        	 setId(string);
	         }
	         
	         if ((string = EvalHelper.evalString("name", getName(), this,pageContext)) != null) {
	        	 setName(string);
	         }
	    }
 
	public int doStartTag() throws JspException {
		 if(this.id == null){
			  throw new JspException("tag id not be null"); 
		 }
		 evaluateExpressions();
		 MenuRepository repository =(MenuRepository) pageContext.getServletContext().getAttribute(MenuRepository.MENU_REPOSITORY_KEY);
		 if (repository == null) {
	            throw new JspException("Could not obtain the menu repository");
	      }
		  if (this.name == null) {
			   List<MenuComponent>  menus = repository.getTopMenus();
			   pageContext.setAttribute(this.id, menus);
	        } else {
	        	MenuComponent  menu = repository.getMenu(this.name);
	            pageContext.setAttribute(this.id, menu);
	        }
		  return (EVAL_BODY_INCLUDE);
	 }
	
	public int doEndTag() throws JspException {
        pageContext.removeAttribute(this.id);
        return (EVAL_PAGE);
    }

}
