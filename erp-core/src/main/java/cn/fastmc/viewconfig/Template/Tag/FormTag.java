package cn.fastmc.viewconfig.Template.Tag;

import cn.fastmc.viewconfig.components.Form;
import cn.fastmc.viewconfig.components.ViewBase;


public class FormTag extends FormTemplateTag {
	private String saveAction;
	private String updateAction;
	private String entity;
	public String getSaveAction() {
		return saveAction;
	}
	public void setSaveAction(String saveAction) {
		this.saveAction = saveAction;
	}
	public String getUpdateAction() {
		return updateAction;
	}
	public void setUpdateAction(String updateAction) {
		this.updateAction = updateAction;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}

	@Override
	public ViewBase getView() {		
		OraForm form = new OraForm();	
		form.setEntity(entity);
		form.setSaveAction(saveAction);
		form.setUpdateAction(updateAction);
		form.setId(id);
		return form;
	}
	
	private class OraForm extends Form{
		@Override
		protected String getTemplate(){
			return "oraform.ftl";
		}
	}
}
