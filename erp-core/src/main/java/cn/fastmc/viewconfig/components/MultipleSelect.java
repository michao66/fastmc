package cn.fastmc.viewconfig.components;

import java.util.Map;

import javax.servlet.jsp.PageContext;

import cn.fastmc.core.utils.FunctionUtils;

public class MultipleSelect extends FormItem {
	private static final String FROM_LIST = "from";
	private static final String TO_LIST = "to";
	
	@Override
	public void end(PageContext context) {
		evaluateParams(context);
		Object fromObj = null,toObj=null;
        if(parameters.containsKey(FROM_LIST)&& parameters.containsKey(FROM_LIST)){
        	fromObj = FunctionUtils.findObjFormSpringContext(context.getServletContext(),(String)parameters.get(FROM_LIST) );
        	toObj = FunctionUtils.findObjFormSpringContext(context.getServletContext(),(String)parameters.get(TO_LIST));
        }
		
		if(fromObj!=null){
			ListDataLoad load = (ListDataLoad)fromObj;
			Map<String,String> values = load.load(this,context);
			context.getRequest().setAttribute("fromItemValue",values);
		}
		if(toObj!=null){
			ListDataLoad load = (ListDataLoad)toObj;
			Map<String,String> values = load.load(this,context);
			context.getRequest().setAttribute("toItemValue",values);
		}
		super.end(context);
	}
	

}
