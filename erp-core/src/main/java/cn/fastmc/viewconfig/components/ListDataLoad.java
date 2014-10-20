package cn.fastmc.viewconfig.components;

import java.util.Map;

import javax.servlet.jsp.PageContext;

public interface ListDataLoad {
	  public Map<String,String> load(FormItem item, PageContext context);
	  
	 
}
