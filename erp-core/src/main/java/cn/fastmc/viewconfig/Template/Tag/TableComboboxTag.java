package cn.fastmc.viewconfig.Template.Tag;

import cn.fastmc.viewconfig.components.InputText;
import cn.fastmc.viewconfig.components.ViewBase;

public class TableComboboxTag extends InputTag{
	
	@Override
	public ViewBase getView() {
		TableCombobox combobox = new TableCombobox();
		combobox.setId(getId());
		handleAttribute(combobox);
		return combobox;
	}
	public class TableCombobox extends InputText{
	    private static final String TEMPLATE = "tablecombobox.ftl";
		public TableCombobox() {
			super();
		}
		
		protected String getDefaultTemplate(){
			return TEMPLATE;
		}

	}
}
