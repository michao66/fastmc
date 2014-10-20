/**
 * 放置SQL 相关参数
 * @author mi
 */
package cn.fastmc.sqlconfig.base;

import java.util.ArrayList;
import java.util.List;

public class DynamicContext {
	 
	 private List<String> parameterMappingNames;
	 
	 private List<Object> parameterValus;
	 
	 private Object parameterObject;
	
	 public Object getParameterObject() {
		return parameterObject;
	}

	public void setParameterObject(Object parameterObject) {
		this.parameterObject = parameterObject;
	}
	private final StringBuilder sqlBuilder = new StringBuilder();
	 
	 public DynamicContext(Object parameterObject){
		 this.parameterObject = parameterObject; 
		 this.parameterMappingNames = new ArrayList<String>();
		 this.parameterValus = new ArrayList<Object>();
	 }
	 
	 public void appendSql(String sql) {
		    sqlBuilder.append(sql);
		    sqlBuilder.append(" ");
	 }
     public void addParameterMappingNames(String context){
    	 if (!this.parameterMappingNames.contains(context)){
    		 this.parameterMappingNames.add(context);
    	 }
     }
     public void addParameterValus(Object obj){
    	 if (!this.parameterValus.contains(obj)){
    		 this.parameterValus.add(obj);
    	 }
     }
     public List<String> getParameterMappingNames(){
    	 return this.parameterMappingNames;
     }
     public List<Object> getParameterValus(){
    	 return this.parameterValus;
     }
	 public String getSql() {
		  return sqlBuilder.toString().trim();
	 }
	 
}
