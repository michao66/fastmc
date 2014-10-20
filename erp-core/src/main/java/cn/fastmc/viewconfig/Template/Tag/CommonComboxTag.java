package cn.fastmc.viewconfig.Template.Tag;

import cn.fastmc.viewconfig.components.CommonCombox;
import cn.fastmc.viewconfig.components.ViewBase;

public class CommonComboxTag extends ComboxTag{
	
	@Override
	public ViewBase getView() {
		CommonCombox combobox = new CommonCombox();
		combobox.setId(getId());
		handleAttribute(combobox);
		handleItemValue(combobox);
		return combobox;
	}
	
	

}
