package cn.fastmc.viewconfig.Template.Tag;

import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;

import cn.fastmc.viewconfig.components.Validator;
import cn.fastmc.viewconfig.components.ViewBase;

public abstract class InputTag extends TemplateBodyTagSupport {

	private String attribute;
	private String validate;
	private String validateMessage;

	

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getValidateMessage() {
		return validateMessage;
	}

	public void setValidateMessage(String validateMessage) {
		this.validateMessage = validateMessage;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}
	@Override
	public int doStartTag() throws JspException {
		component = getView();
		evaluateExpressions();
		if (StringUtils.isNotEmpty(validate)
				|| StringUtils.isNotEmpty(validateMessage)) {
			FormTag tag = ((FormTag) this.getParent());
			tag.getForm().addValidators(handleValidator());
		}
		return EVAL_BODY_INCLUDE;
	}
	@Override
	public int doEndTag() throws JspException {
		component.end(pageContext);
		return EVAL_PAGE;
	}
	@Override
	protected void evaluateExpressions()throws JspException {
		  super.evaluateExpressions();
		  String string = null;
	         if ((string = EvalHelper.evalString("attribute", getId(), this, pageContext)) != null) {
	        	 setAttribute(string);
	         }
	}
	protected void handleAttribute(ViewBase item) {
		if (attribute != null) {
			final StringTokenizer parser = new StringTokenizer(attribute, "|",false);
			while (parser.hasMoreTokens()) {
				String token = StringUtils.trimToEmpty(parser.nextToken());
				int index = token.indexOf("=");
				if (index > 1) {
					item.addParameter(token.substring(0, index),
							token.substring(index + 1));
				}
			}
		}
	}

	protected Validator handleValidator() {
		Validator validator = new Validator();
		String[] vals = StringUtils.split(validate, "|");
		for (int i = 0; i < vals.length; i++) {
			validator.handleValidatorValue(vals[i]);
		}
		String[] arrymessage = StringUtils.split(validateMessage, "|");
		;
		for (int i = 0; i < arrymessage.length; i++) {
			validator.addMessage(arrymessage[i]);
		}
		validator.setId(getId());
		return validator;
	}

}