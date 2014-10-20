package cn.fastmc.viewconfig.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

import cn.fastmc.core.utils.FunctionUtils;
import cn.fastmc.xml.parsing.GenericTokenParser;
import cn.fastmc.xml.parsing.TokenHandler;

public class TemplateParamaterBuilder {//
	public String parse(String originalValue,PageContext context) {
	    ParameternHandler handler = new ParameternHandler(context);
	    GenericTokenParser parser = new GenericTokenParser("#{", "}", handler);
	    String newValue = parser.parse(originalValue);
	    return newValue;
	   
	  }
	 public  boolean isExpression(String strEexp){
		 if( StringUtils.isEmpty(strEexp)){
			 return false;
		 }else{
			 return StringUtils.indexOf(strEexp, "#")>-1;
		 }
		 
	 }
	 private static class ParameternHandler  implements TokenHandler {
        private PageContext context;
        public   ParameternHandler(PageContext context){
        	this.context = context;
        }
		@Override
		public String handleToken(String content) {
			Object newValue = null;
			HttpServletRequest request = (HttpServletRequest) context.getRequest();
			Object obj = context.getRequest().getAttribute(Form.PAGECONTEXT_FORMVALUES);			
			if (null == newValue) {
				newValue = request.getParameter(content);
			}
			if (null == newValue) {
				newValue = request.getAttribute(content);
			} 
			if(null != obj && null == newValue) {
				newValue = FunctionUtils.getPropertyToEmpty(obj,content);
			}
			return StringUtils.trimToEmpty(String.valueOf(newValue));
		}

	 }
	 
	 
	
}
