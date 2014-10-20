package cn.fastmc.viewconfig.components;



public class Fieldset extends FormItem {
	private static final String TEMPLATE = "fieldset.ftl";

	public Fieldset() {
		super();
	}
	private String title;

	
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	protected String getDefaultTemplate(){
		return TEMPLATE;
	}
	
	
}
