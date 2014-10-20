package cn.fastmc.viewconfig.Template.Tag;

import cn.fastmc.viewconfig.components.InputText;
import cn.fastmc.viewconfig.components.ViewBase;

public class DateTextTag  extends InputTag{
	
	@Override
	public ViewBase getView() {
		DateText combobox = new DateText();
		combobox.setId(getId());
		handleAttribute(combobox);
		return combobox;
	}
	
	public class DateText extends InputText{
	    private static final String TEMPLATE = "date.ftl";
		public DateText() {
			super();
		}
		
		protected String getDefaultTemplate(){
			return TEMPLATE;
		}

	}

}
