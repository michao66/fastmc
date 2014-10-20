package cn.fastmc.viewconfig.Template.Tag;

import cn.fastmc.viewconfig.components.Radio;
import cn.fastmc.viewconfig.components.ViewBase;

public class RadioTag extends InputTag {

	@Override
	public ViewBase getView() {
		Radio radio = new Radio();
		radio.setId(getId());
		handleAttribute(radio);
		return radio;
	}

}
