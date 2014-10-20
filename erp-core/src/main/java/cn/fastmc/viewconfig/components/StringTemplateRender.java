package cn.fastmc.viewconfig.components;

import java.io.StringReader;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;

public class StringTemplateRender implements HtmlRender {
    private static final Log LOG = LogFactory.getLog(StringTemplateRender.class);
 
	private Configuration conf ; 
	
	public StringTemplateRender(){
		conf = new Configuration();
		conf.setDefaultEncoding("UTF-8");  

	}
     
	@Override
	public String render(Object parent, Object data, String format) {
		 StringWriter sw = new StringWriter(); 
		try{
			TableHead tableHead = (TableHead)parent;
			Template temp = new Template("tblHtml", new StringReader(tableHead.getBody()), conf);
			SimpleHash root = new SimpleHash();
			root.put("bean", data);
			temp.process(root, sw); 
		}catch(Exception ex){
			LOG.error(ex);
		}
		return sw.toString();
		
	}

}
