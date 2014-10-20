package cn.fastmc.viewconfig.components;


public class HiddenText extends InputText{
	public static final String TEMPLATE = "hidden.ftl";
  
	public HiddenText() {
		super();
	}
	
	protected String getDefaultTemplate(){
		return TEMPLATE;
	}
	
}
