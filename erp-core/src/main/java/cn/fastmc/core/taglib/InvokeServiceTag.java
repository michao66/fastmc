package cn.fastmc.core.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class InvokeServiceTag extends TagSupport {
	public int doStartTag() throws JspException {
		try {
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
			return (EVAL_BODY_INCLUDE);
		}catch(Exception ex) {
			throw new JspException(ex);
		}
	}
}
