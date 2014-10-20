package cn.fastmc.viewconfig.components;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;

public class NumberHtmlRender implements HtmlRender {
	private static final String FORMAT_TEXT="#,###.00";
	public String render(Object parent,Object data,String format){
		if(data== null){
			return "";
		}
		DecimalFormat decimalFormat = new DecimalFormat(StringUtils.defaultIfEmpty(format, FORMAT_TEXT));
		NumberFormat mumformat = NumberFormat.getCurrencyInstance();
		return mumformat.format(decimalFormat.format(data));
		
	}

}
