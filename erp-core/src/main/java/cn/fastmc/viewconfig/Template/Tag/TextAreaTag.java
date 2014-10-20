package cn.fastmc.viewconfig.Template.Tag;

import cn.fastmc.viewconfig.components.TextArea;
import cn.fastmc.viewconfig.components.ViewBase;

public class TextAreaTag extends InputTag {

	@Override
	public ViewBase getView() {
		TextArea text = new TextArea();
		text.setId(getId());
		handleAttribute(text);
		return text;
	}

}
