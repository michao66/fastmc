package cn.fastmc.viewconfig.components;

import java.util.Date;

import cn.fastmc.core.utils.FunctionUtils;

public class DateHtmlRender implements HtmlRender {
	
	public String render(Object parent,Object data,String format){
		if(data== null){
			return "";
		}
		return FunctionUtils.formatDate((Date)data, format);
		
	}
}
