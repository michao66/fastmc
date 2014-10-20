package cn.fastmc.viewconfig.components;

public class TextArea extends InputText{
    private static final String TEMPLATE = "textarea.ftl";
	public TextArea() {
		super();
	}
	
	protected String getDefaultTemplate(){
		return TEMPLATE;
	}
}
