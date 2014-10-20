package cn.fastmc.viewconfig.components;


public class Radio extends FormItem{
    private static final String TEMPLATE = "radio.ftl";
	public Radio() {
		super();
	}
	
	protected String getDefaultTemplate(){
		return TEMPLATE;
	}
	
}
