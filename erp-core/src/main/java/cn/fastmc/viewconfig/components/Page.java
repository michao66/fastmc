package cn.fastmc.viewconfig.components;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class Page extends ViewBase{
	public static final String TEMPLATE = "page.ftl";

	
	private String template;
	private Map<String,Form> forms ;
	private Map<String,Table> tables;
	
	public Page() {
    	super();
    	forms = new HashMap<String,Form>(); 
    	tables = new HashMap<String,Table>(); 
	}

	protected String getDefaultTemplate(){
		if(StringUtils.isNotEmpty(template)){
			return template;
		}
		return TEMPLATE;
	}
	
	public void addForm(Form form){
		forms.put(form.getId(), form);
	}
	
	public void addTable(Table table){
		tables.put(table.getId(),table);
	}
	
	public Form getForm(String formId){
		return forms.get(formId);
	}
	
	public Table getTable(String tableId) {
		return tables.get(tableId);
	}
}
