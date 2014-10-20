package cn.fastmc.viewconfig;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.digester.RuleSetBase;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.Attributes;

import cn.fastmc.viewconfig.components.CombinationFormItem;
import cn.fastmc.viewconfig.components.Combobox;
import cn.fastmc.viewconfig.components.FormItem;
import cn.fastmc.viewconfig.components.MultipleSelect;
import cn.fastmc.viewconfig.components.Radio;
import cn.fastmc.viewconfig.components.TableHead;
import cn.fastmc.viewconfig.components.Validator;

public class ViewRuleSet extends RuleSetBase {

	@Override
	public void addRuleInstances(Digester digester) {
		// TODO Auto-generated method stub
		digester.addObjectCreate("page","cn.fastmc.viewconfig.components.Page","className");
		digester.addSetProperties("page");
		digester.addSetNext("page", "addPage");
		
		digester.addObjectCreate("page/form","cn.fastmc.viewconfig.components.Form","className");
		digester.addSetProperties("page/form");
		digester.addSetNext("page/form","addForm","cn.fastmc.viewconfig.components.Form");
		
	
		//处理验证配置
		digester.addObjectCreate("page/form/validate","cn.fastmc.viewconfig.components.Validator","className");
		digester.addRule("page/form/validate",new setValidateRule());
		digester.addSetNext("page/form/validate","addValidators","cn.fastmc.viewconfig.components.Validator");
		
		digester.addSetNext("page/form","addForm","cn.fastmc.viewconfig.components.Form");

		digester.addRule("page/form/formItem",new createFormItem());
		digester.addSetProperties("page/form/formItem");
		digester.addRule("page/form/formItem/attribute",new addItemAttributeRule());
		digester.addRule("page/form/formItem/itemValue",new setItemVlaue());
		digester.addSetNext("page/form/formItem","addFormItems","cn.fastmc.viewconfig.components.FormItem");
		
		
		digester.addObjectCreate("page/form/combination","cn.fastmc.viewconfig.components.CombinationFormItem");
		digester.addSetProperties("page/form/combination");
		digester.addRule("page/form/combination/list/formItem",new createFormItem());
		digester.addSetProperties("page/form/combination/list/formItem");
		digester.addRule("page/form/combination/list/formItem/attribute",new addItemAttributeRule());
		digester.addRule("page/form/combination/list/formItem/itemValue",new setItemVlaue());
		digester.addSetNext("page/form/combination/list/formItem","addCombinationFormItem","cn.fastmc.viewconfig.components.FormItem");
		digester.addSetNext("page/form/combination","addFormItems","cn.fastmc.viewconfig.components.FormItem");
        
		
		
		digester.addObjectCreate("page/form/fieldset","cn.fastmc.viewconfig.components.Fieldset","className");
		digester.addSetProperties("page/form/fieldset");
		digester.addSetNext("page/form/fieldset","addFormItems","cn.fastmc.viewconfig.components.Fieldset");
		
		
	
		
		
		digester.addObjectCreate("page/table","cn.fastmc.viewconfig.components.Table","className");
		digester.addSetProperties("page/table");
		digester.addSetNext("page/table","addTable","cn.fastmc.viewconfig.components.Table");
		digester.addObjectCreate("page/table/head","cn.fastmc.viewconfig.components.TableHead","className");
		digester.addSetProperties("page/table/head");
		digester.addRule("page/table/head",new putTableHeadStringTemplate());
		digester.addSetNext("page/table/head","addHead","cn.fastmc.viewconfig.components.TableHead");
		
	}
	final class createFormItem extends Rule {
		@Override
		public void begin(Attributes attributes) throws Exception {
			   String type = attributes.getValue("type");
			   FormItem  formitem =null;
			   if("multiple".equals(type)){
				    formitem = new MultipleSelect();  
			   }else if("combobox".equals(type) || "multiplecombobox".equals(type)){
				    formitem = new Combobox();  
			   }else if("combination".equals(type)){
				   formitem = new CombinationFormItem();
			   }else if ("radio".equals(type)){
				   formitem = new Radio();
			   }else{
				   formitem = new FormItem();
			   }
			   digester.push(formitem);
		}
		@Override
		 public void end() throws Exception {
		       digester.pop();
		 }
	}
	final class setValidateRule extends Rule {
		@Override
		public void begin(Attributes attributes) throws Exception {
			Validator item =  (Validator)digester.peek();
			String value = attributes.getValue("value");
			String[] vals = StringUtils.split(value, "|");
			for(int i=0;i<vals.length;i++){
				item.handleValidatorValue(vals[i]);
			}
			item.setId(attributes.getValue("id"));
			String message = StringUtils.defaultIfEmpty(attributes.getValue("message"), "");
			String[] arrymessage = StringUtils.split(message, "|");;
			for(int i=0;i<arrymessage.length;i++){
				item.addMessage(arrymessage[i]);
			}
			
		}
	}
	
	final class setItemVlaue extends Rule {
		@Override
		public void begin(Attributes attributes) throws Exception {
			FormItem  item =  (FormItem)digester.peek();
			String value = attributes.getValue("value");
			String prefix = StringUtils.substring(value, 0, value.indexOf(":"));
			String context = StringUtils.substring(value, value.indexOf(":")+1);
			if(StringUtils.equalsIgnoreCase("list",prefix)){
				item.addParameter("itemValue",doList(context)) ;
			}else if(StringUtils.equalsIgnoreCase("method",prefix)){
				doMethod(item,context);
			}else{
				item.addParameter("itemValue",context); 
			}
		}
		
		private void doMethod(FormItem  item,String context){
			final StringTokenizer parser = new StringTokenizer(context, "|",	false);
			while (parser.hasMoreTokens()) {
				String token =StringUtils.trimToEmpty(parser.nextToken());
				String[] keys = token.split("=");
				item.addParameter(keys[0], keys[1]);
			}
		}
		
		private Map<String,String> doList(String context){
			Map<String,String> valus = new LinkedHashMap<String,String>();
			final StringTokenizer parser = new StringTokenizer(context, "|",	false);
			while (parser.hasMoreTokens()) {
				String token =StringUtils.trimToEmpty(parser.nextToken());
				String[] keys = token.split("=");
				valus.put(keys[0], keys[1]);
			}
			return valus;
		}
	}
	
	final class addItemAttributeRule extends Rule {
		@Override
		public void begin(Attributes attributes) throws Exception {
			String text = attributes.getValue("value");
			if (text != null) {
				FormItem item =  (FormItem)digester.peek();
				final StringTokenizer parser = new StringTokenizer(text, "|",	false);
				while (parser.hasMoreTokens()) {
					String token =StringUtils.trimToEmpty(parser.nextToken());
					int index = token.indexOf("=");
					if(index>1){
						item.addParameter(token.substring(0, index), token.substring(index+1));
					}
					
				}
			}
		}

		public addItemAttributeRule() {
			super();
		}
	}
	
	final class putTableHeadStringTemplate extends Rule{

		@Override
		public void body(String text) throws Exception {
			if (text != null) {
				TableHead  heand = (TableHead)digester.peek();
				heand.setBody(text);
			}
		}
		
	}
}
