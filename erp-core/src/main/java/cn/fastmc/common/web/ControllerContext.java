package cn.fastmc.common.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ControllerContext implements Serializable {

	private static ThreadLocal controllerContext = new ThreadLocal();

	private Map<Object, Object> context;

	private static final String REQUEST = "javax.servlet.http.HttpServletRequest";

	private static final String RESPONSE = "javax.servlet.http.HttpServletResponse";



	public ControllerContext(Map<Object, Object> context) {
		this.context = context;
	}

	public static void setContext(ControllerContext context) {
		controllerContext.set(context);
	}

	public static ControllerContext getContext() {
		ControllerContext context = (ControllerContext) controllerContext.get();
		if (null == context) {
			context = new ControllerContext(new HashMap<Object, Object>());
			setContext(context);

		}
		return context;
	}

	public Map<Object, Object> getContextMap() {
		return this.context;
	}

	public void setContextMap(Map<Object, Object> contextMap) {
		getContext().context = contextMap;
	}

	public Object get(Object key) {
		return context.get(key);
	}

	public void put(Object key, Object value) {
		context.put(key, value);
	}

	public void setRequest(HttpServletRequest request) {
		put(REQUEST, request);
	}

	public HttpServletRequest getRequest() {
		return (HttpServletRequest) get(REQUEST);
	}

	public HttpServletResponse getResponse() {
		return (HttpServletResponse) get(RESPONSE);
	}
	
	public ServletContext getServletContext(){
		return getSession().getServletContext();
	}

	public void setResponse(HttpServletResponse response) {
		put(RESPONSE, response);
	}

	public HttpSession getSession() {
		return getRequest().getSession();
	}

	public ServletContext getApplication() {
		return getSession().getServletContext();
	}
    public  void removeRequest(){
    	context.remove(REQUEST);
    }
    
    public  void removeResponse(){
    	context.remove(RESPONSE);
    }
}
