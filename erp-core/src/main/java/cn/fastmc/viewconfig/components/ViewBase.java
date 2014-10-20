package cn.fastmc.viewconfig.components;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import cn.fastmc.core.utils.FunctionUtils;
import cn.fastmc.viewconfig.Template.FreemarkerTemplateEngine;
import cn.fastmc.viewconfig.Template.TemplateRenderingContext;

public abstract class ViewBase implements UIBase {
	private static final Log LOG = LogFactory.getLog(ViewBase.class);
	protected TemplateParamaterBuilder paramaterBuilder = new TemplateParamaterBuilder();
	protected Map<String, Object> parameters;
	protected String id;
	
	protected abstract String getDefaultTemplate();

	protected void evaluateValue(PageContext context) {
		try {
			Object obj = context.getRequest().getAttribute(Form.PAGECONTEXT_FORMVALUES);
			String valueExp = (String)parameters.get("value");
			Object value = null;
			if(paramaterBuilder.isExpression(valueExp)){
				value = paramaterBuilder.parse(valueExp, context);
			}
			if ( null == value && null != obj  ) {
				value = FunctionUtils.getPropertyToEmpty(obj, getId());
			}
			if (null == value) {
				value = context.getRequest().getParameter(getId());// 再取转参
			}
			context.getRequest().setAttribute("inputValue",null!=value?StringUtils.trimAllWhitespace(String.valueOf(value)):value);

		} catch (Exception e) {
			LOG.error(e);
		}
	}

	public ViewBase() {
		parameters = new LinkedHashMap<String, Object>();
	}

	protected void evaluateParams(PageContext context) {
		addParameter("id", id);
		if(null != parameters.get("url")){
			String newValue = paramaterBuilder.parse((String)parameters.get("url"), context);
			context.getRequest().setAttribute("url", newValue);
		}
	}

	protected Object findValue(Object bean, String properties) {
		Object obj = null;
		try {
			obj = PropertyUtils.getNestedProperty(bean, properties);
		} catch (Exception ex) {
			LOG.equals(ex);
		}
		return obj;
	}

	public void end(PageContext context) {
		try {
			mergeTemplate(context);
			context.getRequest().removeAttribute("inputValue");
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	protected void mergeTemplate(PageContext context) throws Exception {
		final TemplateRenderingContext templatecontext = new TemplateRenderingContext(context, this);
		FreemarkerTemplateEngine.renderTemplate(templatecontext,getDefaultTemplate());
	}

	public void start(PageContext context) {
		evaluateParams(context);
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void addAllParameters(Map<String, String> params) {
		parameters.putAll(params);
	}

	public void addParameter(String key, Object value) {
		if (key != null) {
			Map<String, Object> params = getParameters();
			if (value == null) {
				params.remove(key);
			} else {
				params.put(key, value);
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
