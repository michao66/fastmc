package cn.fastmc.viewconfig.components;

public class Text extends InputText{
    private static final String TEMPLATE = "text.ftl";
	public Text() {
		super();
	}
	
	protected String getDefaultTemplate(){
		return TEMPLATE;
	}

}
