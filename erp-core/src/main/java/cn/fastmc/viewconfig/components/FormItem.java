package cn.fastmc.viewconfig.components;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;

public class FormItem extends ViewBase {
	private static final String TEMPLATES = "decorate.ftl";
	private static final String Hidden_TEMPLATES = "hidden.ftl";
	private String label;
	private String type;
	private boolean singlecolumn = false;
	private int colspan;
	private boolean updateHidde;
	private boolean updateOnlyRead = false;

	

	public boolean isUpdateHidde() {
		return updateHidde;
	}

	public void setUpdateHidde(boolean updateHidde) {
		this.updateHidde = updateHidde;
	}

	public boolean isSinglecolumn() {
		return singlecolumn;
	}

	public void setSinglecolumn(boolean singlecolumn) {
		this.singlecolumn = singlecolumn;
	}

	public FormItem() {
		super();
	}

	protected String getDefaultTemplate() {
		return isHidden()?Hidden_TEMPLATES:TEMPLATES;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return StringUtils.trim(type);
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isUpdateOnlyRead() {
		return updateOnlyRead;
	}

	public void setUpdateOnlyRead(boolean updateOnlyRead) {
		this.updateOnlyRead = updateOnlyRead;
	}
	@Override
	public void end(PageContext context) {
		evaluateParams(context);
		evaluateValue(context);
		super.end(context);

	}
    public boolean isHidden(){
    	return "hidden".equals(type);
    }
   
	@Override
	protected void evaluateParams(PageContext context) {
		super.evaluateParams(context);
		if(updateOnlyRead){
			addParameter("updateReadonly",true);
		}
	}

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

}
