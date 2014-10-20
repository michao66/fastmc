package cn.fastmc.viewconfig.components;

public class MultipleCombobox extends Combobox{
    private static final String TEMPLATE = "multiplecombobox.ftl";
	public MultipleCombobox() {
		super();
	}
	
	protected String getDefaultTemplate(){
		return TEMPLATE;
	}

}
