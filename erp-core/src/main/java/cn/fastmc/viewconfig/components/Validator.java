package cn.fastmc.viewconfig.components;
/**
 * 用于jquery Validator
 */
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class Validator {
	private  Form parent;
	private  String id;	
	private  Map<String,String> validatorValue;
	private  List<String> message;
	
	public Validator(){
		validatorValue = new LinkedHashMap<String,String>();
		message = new ArrayList<String>();
	}
	
	public Validator(Form parent){
		this.parent = parent;
	}
	
	public Form getParent() {
		return parent;
	}

	public void setParent(Form parent) {
		this.parent = parent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String,String>  getValidatorValue() {
		return validatorValue;
	}

	public void andValidatorValue(String validatorRole,String validatorValue) {
		this.validatorValue.put(validatorRole, validatorValue);
	}

	public List<String> getMessage() {
		return message;
	}

	public void setMessage(List<String> message) {
		this.message = message;
	}
	public void addMessage(String message){
		this.message.add(message);
	}
	public void handleValidatorValue(String value){
		if(StringUtils.equalsIgnoreCase(value, "*")){//不可为空
			andValidatorValue("required","true"); 
		}else if(StringUtils.equalsIgnoreCase(value, "N")){//数值
			andValidatorValue("number","true");
		}else if(StringUtils.equalsIgnoreCase(value, "E")){//邮件
			andValidatorValue("email","true");
		}else if(StringUtils.equalsIgnoreCase(value, "D")){//日期
			andValidatorValue("date","true");
		}else if(StringUtils.equalsIgnoreCase(value, "Z")){//必须输入整数
			andValidatorValue("digits","true");
		}else{
			String[]temp = StringUtils.split(value, ":");
			andValidatorValue(temp[0],temp[1]);
		}
	}
	/*
	public String toString(){
		StringBuilder sb = new StringBuilder();
		Set<String> validatorRoles = validatorValue.keySet();
		int i=0;
		for(String validatorRole : validatorRoles){
			sb.append(validatorRole +" = \"" + validatorValue.get(validatorRole)+"\" ");
			if(message.size() > i){
				sb.append("title= \"" + message.get(i)+"\" ");
			}
			i++;
		}
		return sb.toString();
		
	}
	*/

}
