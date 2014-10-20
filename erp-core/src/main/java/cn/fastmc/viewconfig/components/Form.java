package cn.fastmc.viewconfig.components;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.fastmc.viewconfig.Template.FreemarkerTemplateEngine;
import cn.fastmc.viewconfig.Template.TemplateRenderingContext;

public class Form extends ViewBase{
    private static final Log LOG = LogFactory.getLog(Form.class);

    private static final String TEMPLATE = "form.ftl";
    private static final String CLOSE_TEMPLATE = "close_form.ftl";
    public static final String PAGECONTEXT_FORMVALUES = "form_values";
    public static final String FORM_ACTION_URL = "formUrl";
    public static final String FORM_UPDATESTATE = "form_updateState";
	private String saveAction;
	private String viewAction;
	private String updateAction;
    private boolean haveSubmitBtn;
	
	private String entity;
	private List<Validator> validators;
	private int column = 1;
	List<List<FormItem>> group =  new ArrayList<List<FormItem>>();

	

	public Form() {
    	super();
    	validators = new ArrayList<Validator>(); 
    	haveSubmitBtn = true;
    	
	}
	
	protected String getTemplate(){
		return TEMPLATE;
	}
    @Override
    public void start(PageContext context) {
    	try {	
    		super.start(context);
			final TemplateRenderingContext templatecontext = new TemplateRenderingContext(context,this);
			FreemarkerTemplateEngine.renderTemplate(templatecontext,getTemplate());
		} catch (Exception e) {
            LOG.error("Could not open template", e);
            e.printStackTrace();
        }
	}
    
	protected String getDefaultTemplate(){
		return CLOSE_TEMPLATE;
	}
	
	public void addFormItems(FormItem item){
		if("file".equals(item.getType())){
			this.addParameter("entity", "multipart/form-data");
		}
		 Validator v =findValidator(item.id);
		 if(v != null){
			 item.addParameter("validator", v);
		 }
		 if (item.isSinglecolumn() || item instanceof  Fieldset) {
				item.addParameter("rowClass", "span12");
				group.add(new ArrayList<FormItem>());
				group.get(group.size()-1).add(item);
				group.add(new ArrayList<FormItem>());
		 }else{
			    if(group.size() == 0){
			    	group.add(new ArrayList<FormItem>());
			    }
			    if(group.get(group.size()-1).size() >= getColumn()){
			    	group.add(new ArrayList<FormItem>());
			    }
			    List<FormItem> lastRows =  group.get(group.size()-1);
			    item.addParameter("rowClass", "span"+(12/getColumn()));
			    lastRows.add(item);
		} 
	}
	
	
	public String getSaveAction() {
		return saveAction;
	}
	public void setSaveAction(String saveAction) {
		this.saveAction = saveAction;
	}
	public String getViewAction() {
		return viewAction;
	}
	public void setViewAction(String viewAction) {
		this.viewAction = viewAction;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public void addValidators(Validator v){
		this.validators.add(v);
	}
	public List<Validator> getValidators(){
		return this.validators;
	}
	public Validator findValidator(String id){
		if(StringUtils.isEmpty(id)){
			return null;
		}
		Validator res = null;
		for(Validator v :validators){
			if(v.getId().equals(id)){
				res = v;
				break;
			}
		}
		return res;
	}
	public String getUpdateAction() {
		if(StringUtils.isEmpty(updateAction)){
			updateAction = this.saveAction;
		}
		return updateAction;
	}
	public void setUpdateAction(String updateAction) {
		this.updateAction = updateAction;
	}
	
	public boolean isHaveSubmitBtn() {
		return haveSubmitBtn;
	}
	public void setHaveSubmitBtn(boolean haveSubmitBtn) {
		this.haveSubmitBtn = haveSubmitBtn;
	}
	
	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	public List<List<FormItem>> getGroup() {
		return group;
	}

	
}
