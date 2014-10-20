package cn.fastmc.core.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;

import cn.fastmc.core.ResultMessage;
import cn.fastmc.viewconfig.components.Form;

import com.alibaba.fastjson.JSON;

/**
 * HttpServletResponse帮助类
 * 
 * @author liufang
 * 
 */
public final class ResponseUtils {
	public static final Logger log = LoggerFactory.getLogger(ResponseUtils.class);
	protected static final String STATUS = "status";
	protected static final String WARN = "warn";
	protected static final String SUCCESS = "success";
	protected static final String ERROR = "error";
	protected static final String MESSAGE = "message";
	// 输出，返回null
		public static void render(HttpServletResponse response, String content, String type) {
			try {
				response.setContentType(type + ";charset=UTF-8");
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
				response.getWriter().write(content);
				response.getWriter().flush();
			} catch (IOException e) {
				log.error(e.toString());
			}
		}

		// 输出文本，返回null
		public static void renderText(HttpServletResponse response, String text) {
			 render(response, text, "text/plain");
		}

		// 输出HTML，返回null
		public static void renderHtml(HttpServletResponse response, String html) {
			render(response, html, "text/html");
		}

		// 输出XML，返回null
		public static void render(HttpServletResponse response, String xml) {
			render(response, xml, "text/xml");
		}

		// 根据字符串输出JSON，返回null
		public static void renderJson(HttpServletResponse response, String jsonString) {
			 render(response, jsonString, "text/html");
		}

		// 根据Map输出JSON，返回null
		public static void renderJson(HttpServletResponse response,
				Map<String, String> jsonMap) {
			 render(response, JSON.toJSONString(jsonMap), "text/html");
		}

		// 输出JSON警告消息，返回null
		public static void ajaxJsonWarnMessage(HttpServletResponse response,
				String message) {
			Map<String, String> jsonMap = new HashMap<String, String>();
			jsonMap.put(STATUS, WARN);
			jsonMap.put(MESSAGE, message);
			render(response, JSON.toJSONString(jsonMap), "text/html");
		}

		// 输出JSON成功消息，返回null
		public static void renderAjaxJsonSuccessMessage(HttpServletResponse response,
				String message) {
			Map<String, String> jsonMap = new HashMap<String, String>();
			jsonMap.put(STATUS, SUCCESS);
			jsonMap.put(MESSAGE, message);
			render(response, JSON.toJSONString(jsonMap), "text/html");
		}

		// 输出JSON成功消息，返回null
		public static void renderAjaxMessageMessage(HttpServletResponse response,
				ResultMessage message) {
			Map<String, String> jsonMap = new HashMap<String, String>();
			jsonMap.put(STATUS, message.getStatus() > 0 ? SUCCESS : ERROR);
			jsonMap.put(MESSAGE, message.getMessage());
			render(response, JSON.toJSONString(jsonMap), "text/html");
		}

		// 输出JSON错误消息，返回null
		public static void renderAjaxJsonErrorMessage(HttpServletResponse response,
				String message) {
			Map<String, String> jsonMap = new HashMap<String, String>();
			jsonMap.put(STATUS, ERROR);
			jsonMap.put(MESSAGE, message);
			render(response, JSON.toJSONString(jsonMap), "text/html");
		}

		// 设置页面不缓存
		public static void setResponseNoCache(HttpServletResponse response) {
			response.setHeader("progma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 0);
		}
		
		public static ModelAndView buildDefaultInputView(Object bindingEntity,String pageId,String formId,String templatePath) {
			 Assert.notNull(pageId, "pageId action  path  must not be null");
			 Assert.notNull(formId, "formId action  method  must not be null");
			 templatePath = templatePath==null?"/common/commForm":templatePath;
			 ModelAndView model = new ModelAndView(templatePath);
	    	 model.addObject("pageId",pageId);
	    	 model.addObject("formId",formId);
	    	 model.addObject(Form.PAGECONTEXT_FORMVALUES, bindingEntity);
		     return model;
		}
	
	
}
