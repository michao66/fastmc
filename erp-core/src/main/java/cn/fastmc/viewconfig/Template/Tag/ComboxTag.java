package cn.fastmc.viewconfig.Template.Tag;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import cn.fastmc.viewconfig.components.ViewBase;

public abstract class ComboxTag extends InputTag {
	
	

	private String itemValue;

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	protected void handleItemValue(ViewBase combobox) {
		if (itemValue != null) {
			String prefix = StringUtils.substring(itemValue, 0,itemValue.indexOf(":"));
			String context = StringUtils.substring(itemValue,itemValue.indexOf(":") + 1);
			if (StringUtils.equalsIgnoreCase("list", prefix)) {
				combobox.addParameter("itemValue", doList(context));
			} else if (StringUtils.equalsIgnoreCase("method", prefix)) {
				doMethod(combobox, context);
			} else {
				combobox.addParameter("itemValue", context);
			}
		}
	}

	private void doMethod(ViewBase item, String context) {
		final StringTokenizer parser = new StringTokenizer(context, "|", false);
		while (parser.hasMoreTokens()) {
			String token = StringUtils.trimToEmpty(parser.nextToken());
			String[] keys = token.split("=");
			item.addParameter(keys[0], keys[1]);
		}
	}

	private Map<String, String> doList(String context) {
		Map<String, String> valus = new LinkedHashMap<String, String>();
		final StringTokenizer parser = new StringTokenizer(context, "|", false);
		while (parser.hasMoreTokens()) {
			String token = StringUtils.trimToEmpty(parser.nextToken());
			String[] keys = token.split("=");
			valus.put(keys[0], keys[1]);
		}
		return valus;
	}

}