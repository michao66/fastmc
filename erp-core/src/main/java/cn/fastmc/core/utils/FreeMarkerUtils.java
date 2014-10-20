package cn.fastmc.core.utils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.fastmc.core.ServiceException;
import freemarker.ext.servlet.AllHttpScopesHashModel;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.HttpSessionHashModel;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class FreeMarkerUtils {
	
	public static String process(String template, Map<String, ?> model)  {
	    Configuration configuration = getFreeMarkerConfiguration();
	    return process(template, model, configuration);
	  }
   /**
    * 根据
    * @param template
    * @param model
    * @param configuration
    * @return
    */
	public static String process(String template, Map<String, ?> model,Configuration configuration) throws ServiceException {
		if (template == null)
			return null;
		if (configuration == null)
			configuration = new Configuration();
		StringWriter stringWriter = new StringWriter();
		try {
			new Template("template", new StringReader(template), configuration).process(model, stringWriter);
		} catch (TemplateException ex) {
			throw new ServiceException(ex);
		} catch (IOException ioex) {
			throw new ServiceException(ioex);
		}
		return stringWriter.toString();
	}
	
	public static Configuration getFreeMarkerConfiguration(){
		 Configuration configuration = null;
		    ApplicationContext applicationContext = SpringUtils.getApplicationContext();
		    if (applicationContext != null){
		      FreeMarkerConfigurer configurer = (FreeMarkerConfigurer)SpringUtils.getBean("freeMarkerConfigurer", FreeMarkerConfigurer.class);
		      if (configurer != null)
		    	  configuration = configurer.getConfiguration();
		    }
		    return configuration;
	}
    
	
	private static HttpSessionHashModel buildSessionModel(HttpServletRequest request, HttpServletResponse response, ObjectWrapper wrapper) {
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	      return new HttpSessionHashModel(session, wrapper);
	    }
	    return new HttpSessionHashModel(null, request, response, wrapper);
	  }
	
	  public static SimpleHash buildTemplateModel(ServletContext servletContext, HttpServletRequest request, HttpServletResponse response)
	  {
	    SimpleHash fmModel = new AllHttpScopesHashModel(ObjectWrapper.DEFAULT_WRAPPER, servletContext, request);
	    fmModel.put("Session", buildSessionModel(request, response, ObjectWrapper.DEFAULT_WRAPPER));
	    fmModel.put("Request", new HttpRequestHashModel(request, response, ObjectWrapper.DEFAULT_WRAPPER));
	    fmModel.put("JspTaglibs", servletContext.getAttribute(".freemarker.JspTaglibs"));
	    fmModel.put("base", request.getContextPath() + "/");
	    return fmModel;
	  }

	 

}
