package cn.fastmc.viewconfig.components;

import java.util.Map;

import javax.servlet.jsp.PageContext;

import cn.fastmc.core.utils.FunctionUtils;

public class Combobox extends FormItem {
	private static final String FROM_LIST = "loader";

	
	protected void evaluateValue(PageContext context) {
		super.evaluateValue(context);
		Object loader = null;
        if(parameters.get(FROM_LIST)!=null){
    		 loader = FunctionUtils.findObjFormSpringContext(context.getServletContext(),(String)this.parameters.get(FROM_LIST) );
        }	
		if(loader!=null){
			ListDataLoad load = (ListDataLoad)loader;
			Map<String,String> values = load.load(this,context);
			context.getRequest().setAttribute("itemValue", values);
		}
	}
	
	@Override
	public void end(PageContext context) {
		super.end(context);
		context.getRequest().removeAttribute("itemValue");
	}
}
