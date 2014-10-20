package cn.fastmc.viewconfig.components;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.fastmc.viewconfig.Template.FreemarkerTemplateEngine;
import cn.fastmc.viewconfig.Template.TemplateRenderingContext;

public class CombinationFormItem extends FormItem {
	private static final String TEMPLATES = "combination.ftl";
    private static final String CLOSE_TEMPLATE = "close_combination.ftl";

	private static final Log LOG = LogFactory.getLog(Form.class);

	private List<FormItem> combinations;

	public List<FormItem> getCombinations() {
		return combinations;
	}

	protected String getDefaultTemplate() {
		return TEMPLATES;
	}
	
	public void end(PageContext context) {
		final TemplateRenderingContext templatecontext = new TemplateRenderingContext(context, this);
		FreemarkerTemplateEngine.renderTemplate(templatecontext,TEMPLATES);
		evaluateParams(context);
        for(FormItem item:combinations){
        	item.end(context);
        }
		FreemarkerTemplateEngine.renderTemplate(templatecontext,CLOSE_TEMPLATE);
	}

	public CombinationFormItem() {
		super();
		combinations = new ArrayList<FormItem>();
	}

	public void addCombinationFormItem(FormItem Itmes) {
		combinations.add(Itmes);
	}
	
}
