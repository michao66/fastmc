package cn.fastmc.viewconfig.components;


public class CommonCombox extends Combobox{
    private static final String TEMPLATE = "combobox.ftl";
	public CommonCombox() {
		super();
	}
	
	protected String getDefaultTemplate(){
		return TEMPLATE;
	}
}
