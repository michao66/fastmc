package cn.fastmc.viewconfig.components;

import javax.servlet.jsp.PageContext;

public abstract class InputText extends ViewBase{
	@Override
	public void end(PageContext context) {
		evaluateParams(context);
		evaluateValue(context);
		super.end(context);

	}
	
	
}
