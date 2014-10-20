package cn.fastmc.viewconfig.Template.Tag;

import cn.fastmc.viewconfig.components.TreeCombobox;
import cn.fastmc.viewconfig.components.ViewBase;

public class TreeComboboxTag extends ComboxTag{
	
	@Override
	public ViewBase getView() {
		TreeCombobox combobox = new TreeCombobox();
		combobox.setId(getId());
		handleAttribute(combobox);
		handleItemValue(combobox);
		return combobox;
	}
	
	
}
