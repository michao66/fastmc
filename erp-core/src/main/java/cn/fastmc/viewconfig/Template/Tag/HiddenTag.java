package cn.fastmc.viewconfig.Template.Tag;

import cn.fastmc.viewconfig.components.HiddenText;
import cn.fastmc.viewconfig.components.ViewBase;

public class HiddenTag extends InputTag {

	@Override
	public ViewBase getView() {
		HiddenText hidden = new HiddenText();
		hidden.setId(getId());
		handleAttribute(hidden);
		return hidden;
	}

}
