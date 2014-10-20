package cn.fastmc.core.template;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.util.Assert;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;

public abstract class BaseDirective implements TemplateDirectiveModel {

	protected Object getParameter(String name, Class<?> type,
			Map<String, TemplateModel> params) throws TemplateModelException {
		Assert.hasText(name);
		Assert.notNull(type);
		Assert.notNull(params);
		TemplateModel model = (TemplateModel) params.get(name);
		if (model == null)
			return null;
		Object obj = DeepUnwrap.unwrap(model);
		return ConvertUtils.convert(obj, type);
	}

	protected TemplateModel getVariable(String name, Environment env)
			throws TemplateModelException {
		Assert.hasText(name);
		Assert.notNull(env);
		return env.getVariable(name);
	}

	private void doSetVariable(String name, Object value, Environment env)
			throws TemplateModelException {
		Assert.hasText(name);
		Assert.notNull(env);
		if ((value instanceof TemplateModel))
			env.setVariable(name, (TemplateModel) value);
		else
			env.setVariable(name, ObjectWrapper.BEANS_WRAPPER.wrap(value));
	}

	@SuppressWarnings("unchecked")
	private static void doSetVariables(Map<String, Object> variables,
			Environment env) throws TemplateModelException {
		
		Assert.notNull(variables);
		Assert.notNull(env);
		Iterator it = variables.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			Object value = entry.getValue();
			if ((value instanceof TemplateModel))
				env.setVariable(key, (TemplateModel) value);
			else
				env.setVariable(key, ObjectWrapper.BEANS_WRAPPER.wrap(value));
		}
	}

	protected void setVariables(String key, Object paramObject,
			Environment environment, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		
		TemplateModel model = getVariable(key, environment);
		doSetVariable(key, paramObject, environment);
		body.render(environment.getOut());
		doSetVariable(key, model, environment);
	}

	protected void setVariables(Map<String, Object> model,
			Environment environment, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		Iterator<String> it = model.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			TemplateModel templateModel = getVariable(key, environment);
			map.put(key, templateModel);
		}
		doSetVariables(model, environment);
		body.render(environment.getOut());
		doSetVariables(map, environment);
	}
}

