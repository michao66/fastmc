package cn.fastmc.sqlconfig.base;

import ognl.Ognl;

import org.apache.commons.lang.StringUtils;

import cn.fastmc.core.SqlConfigBuilderException;
import cn.fastmc.xml.parsing.GenericTokenParser;
import cn.fastmc.xml.parsing.TokenHandler;

public class TextSqlNode implements SqlNode {
	private String text;

	public TextSqlNode(String text) {
	    this.text = StringUtils.trimToEmpty(text);
	}
	public boolean apply(DynamicContext context){
		 GenericTokenParser parser = new GenericTokenParser("${", "}", new BindingTokenParser(context));
		 context.appendSql(parser.parse(text));
		 return true;
	}
	
	 private static class BindingTokenParser implements TokenHandler {
		 private DynamicContext context;

		 public BindingTokenParser(DynamicContext context) {
		     this.context = context;
		 }
		 public String handleToken(String content) {
		      try {
		    	  Object value = Ognl.getValue(content, context.getParameterObject());
		          return String.valueOf(value);
		       } catch (Exception e) {
		          throw new SqlConfigBuilderException("Error evaluating expression '" + content + "'. Cause: " + e, e);
		      }
		 }

	 }
	 public String getText(){
		 return text;
	 }
	 @Override
     public boolean equals( Object obj){
		 if(obj instanceof TextSqlNode){
			 return text.equals(((TextSqlNode)obj).getText());
		 }else{
			 return false;
		 }
		
     }
}
