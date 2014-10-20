package cn.fastmc.viewconfig.components;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.fastmc.core.ServiceException;
import cn.fastmc.core.utils.FunctionUtils;

public class HtmlRenderFactory {
    private static final Log LOG = LogFactory.getLog(HtmlRenderFactory.class);
	private static  Object lazz = new Object();
	private static Map<String,HtmlRender> defaultRender = new HashMap<String,HtmlRender>(){
		{
		    put("date", new DateHtmlRender());
		    put("money", new DateHtmlRender());
		    put("check", new CheckboxRender());
		    put("string", new StringRender());
		    put("stringTemplate", new StringTemplateRender());
		}
	};
	
	public static HtmlRender getRender(String type)  throws ServiceException{
		synchronized(lazz){
			HtmlRender render = defaultRender.get(type);
			if(null == render&&StringUtils.lastIndexOf(type, ".")>0){
				try{
					render = (HtmlRender)FunctionUtils.applicationInstance(type);
					defaultRender.put(type, render);
				}catch(Exception ex){
					LOG.error(ex);
					throw new ServiceException(ex);
				}
				defaultRender.put(type, render);
			}
			if(null == render){
				LOG.error("render class "+ type +"no find");
				throw new ServiceException("render class "+ type +"no find");
			}
			return render;
		}
	}
	
	 private static class StringRender implements HtmlRender {
	    	public String render(Object parent,Object data,String format){
	    		if(data== null){
	    			return "";
	    		}
	    		return String.valueOf(data);
	        }
	   }
	    
	 private static class CheckboxRender implements HtmlRender {
	    	public String render(Object parent,Object data,String format){
	    		String check = "<input type=\"checkbox\" name=\"ids\" value=\""+String.valueOf(data)+"\" />";
	    		return check;
	        }
	   }
	
}
