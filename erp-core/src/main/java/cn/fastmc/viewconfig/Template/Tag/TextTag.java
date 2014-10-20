package cn.fastmc.viewconfig.Template.Tag;

import cn.fastmc.viewconfig.components.Text;
import cn.fastmc.viewconfig.components.ViewBase;

public class TextTag extends InputTag {
	
	@Override
	public ViewBase getView() {
		Text text = new Text();
		text.setId(getId());
		handleAttribute(text);
		return text;
	}
	
}
