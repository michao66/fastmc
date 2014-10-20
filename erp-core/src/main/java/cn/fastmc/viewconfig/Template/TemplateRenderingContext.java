package cn.fastmc.viewconfig.Template;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import cn.fastmc.viewconfig.components.ViewBase;

public class TemplateRenderingContext {

	private HttpServletRequest request;
	private HttpServletResponse  response;
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	ViewBase tag;
	
	private PageContext context;
	private Writer writer;

	public TemplateRenderingContext(PageContext context,ViewBase tag) {
		this.context = context;
		this.tag = tag;
		this.writer = context.getOut();
		this.request =  (HttpServletRequest)context.getRequest();
        this.response = (HttpServletResponse)context.getResponse();
	}
	public Writer getWriter() throws IOException{
		return this.writer;
	}
	public ServletContext getServletContext() {
		return this.context.getServletContext();
	}
	public PageContext getPageContext(){
		return this.context;
	}
    public HttpServletRequest  getRequest(){
    	return request;
    }
    public ViewBase getTag() {
		return tag;
	}
	public void setTag(ViewBase tag) {
		this.tag = tag;
	}

	

}
