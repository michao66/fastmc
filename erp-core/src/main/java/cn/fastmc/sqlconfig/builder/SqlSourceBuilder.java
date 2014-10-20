package cn.fastmc.sqlconfig.builder;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import ognl.Ognl;

import org.springframework.util.NumberUtils;

import cn.fastmc.core.SqlConfigBuilderException;
import cn.fastmc.core.utils.FunctionUtils;
import cn.fastmc.sqlconfig.base.SqlText;
import cn.fastmc.sqlconfig.base.TypeConvertHander;
import cn.fastmc.xml.parsing.GenericTokenParser;
import cn.fastmc.xml.parsing.TokenHandler;

public class SqlSourceBuilder {
	
	public SqlText parse(String originalSql, String parameterType,Object  parameterObject) {
	    ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler(parameterType, parameterObject);
	    GenericTokenParser parser = new GenericTokenParser("#{", "}", handler);
	    String sql = parser.parse(originalSql);
	    return new SqlText(sql,handler.getParameterValues());
	  }
	private static class NumberConvertHander implements TypeConvertHander{
	
		private Class type;
		public NumberConvertHander(Class type){
			this.type = type;
		}
		@SuppressWarnings("unchecked")
		public Object convert(Object value){
			return NumberUtils.parseNumber((String)value, type);			
		}
	}
	
	private static class ListConvertHander implements TypeConvertHander{

	
		public Object convert(Object sqlValue) {
			if (String.class.isAssignableFrom(sqlValue.getClass())) {
				String[] arrayvlaue = ((String) sqlValue).split(",");
				return Arrays.asList(arrayvlaue);
			}
			return sqlValue;
		}
	}
	
	private static class DateConvertHander implements TypeConvertHander{
		public Object convert(Object sqlValue){
			return FunctionUtils.String2Date((String)sqlValue);
		}
	}
	
	private static class ParameterMappingTokenHandler  implements TokenHandler {
		  private Properties parameterValues ;
		  
		  private String parameterType;
		  private Object parameterObject;
		  public ParameterMappingTokenHandler(String parameterType,Object parameterObject ){
			  parameterValues = new Properties();
			  this.parameterType = parameterType;
			  this.parameterObject = parameterObject;
		  }
		  
		  public Properties getParameterValues(){
			  return parameterValues;
		  }
		  public String handleToken(String content) {
			  StringTokenizer parameterMappingParts = new StringTokenizer(content, ", \n\r\t");
		      String propertyWithJdbcType = parameterMappingParts.nextToken();
		      String property = extractPropertyName(propertyWithJdbcType);
		      String jdbcType = extractJdbcType(propertyWithJdbcType);
		      parameterValues.put(property, buildParameter(property,jdbcType));
		      return ":"+property;
		  }
		  public Object buildParameter(String property,String jdbcType){			
		      jdbcType = jdbcType==null?parameterType:jdbcType;
		      Object sqlValue = null;
		      try {
		        sqlValue = Ognl.getValue(property,parameterObject);
			  } catch (Exception e) {
		          throw new SqlConfigBuilderException("Error evaluating expression '" + property + "'. Cause: " + e, e);
		      }
		      if(!(parameterObject instanceof Map)){
		    	 return  sqlValue;
		      }
		      if("Integer".equals(jdbcType)){
		    	  final NumberConvertHander  hander = new NumberConvertHander(Integer.class);
		    	  return hander.convert(sqlValue);
		      }else if("Long".equals(jdbcType)){
		    	  final NumberConvertHander  hander = new NumberConvertHander(Long.class);
		    	  return hander.convert(sqlValue);
		      }else if("Float".equals(jdbcType)){
		    	  final NumberConvertHander  hander = new NumberConvertHander(Float.class);
		    	  return hander.convert(sqlValue);
		      }else if("Double".equals(jdbcType)){
		    	  final NumberConvertHander  hander = new NumberConvertHander(Double.class);
		    	  return hander.convert(sqlValue);
		      }else if("List".equals(jdbcType)){
		    	  final ListConvertHander  hander = new ListConvertHander();
		    	  return hander.convert(sqlValue);
		      }else if("Date".equals(jdbcType)){
		    	  final DateConvertHander  hander = new DateConvertHander();
		    	  return hander.convert(sqlValue);
		      }else{
		    	  return (String)sqlValue;
		      }
		  }
		  
		  private String extractPropertyName(String property) {
		      if (property.contains(":")) {
		        StringTokenizer simpleJdbcTypeParser = new StringTokenizer(property, ": ");
		        if (simpleJdbcTypeParser.countTokens() == 2) {
		          return simpleJdbcTypeParser.nextToken();
		        }
		      }
		      return property;
		    }

		    private String extractJdbcType(String property) {
		      if (property.contains(":")) {
		        StringTokenizer simpleJdbcTypeParser = new StringTokenizer(property, ": ");
		        if (simpleJdbcTypeParser.countTokens() == 2) {
		          simpleJdbcTypeParser.nextToken();
		          return simpleJdbcTypeParser.nextToken().trim();
		        }
		      }
		      return null;
		    }
	   }

	
}
