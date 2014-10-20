package cn.fastmc.viewconfig.components;


import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.fastmc.viewconfig.Template.FreemarkerTemplateEngine;
import cn.fastmc.viewconfig.Template.TemplateRenderingContext;
import cn.fastmc.viewconfig.Template.Tag.FormItemTemplateTag;

public class FormRow extends FormItem{
	private static final Log LOG = LogFactory.getLog(FormItemTemplateTag.class);

	public static final String TEMPLATE = "row.ftl";
	public static final String CLOSE_TEMPLATE = "close_row.ftl";
	public FormRow() {
		super();
	}
	
	protected String getDefaultTemplate(){
		return CLOSE_TEMPLATE;
	}
	
	@Override
    public void start(PageContext context) {
    	try {	
			final TemplateRenderingContext templatecontext = new TemplateRenderingContext(context,this);
			FreemarkerTemplateEngine.renderTemplate(templatecontext, TEMPLATE);
		} catch (Exception e) {
            LOG.error("Could not open template", e);
            e.printStackTrace();
        }
	}

}
