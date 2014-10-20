package cn.fastmc.sqlconfig.base;

import java.util.Properties;


public class SqlText {
    public SqlText(String sql, Properties parameterValues,Class entity) {
		this.sql = sql;
		this.parameterValues = parameterValues;
		this.entity = entity ;
	}
    public SqlText(String sql, Properties parameterValues) {
    	this.sql = sql;
		this.parameterValues = parameterValues;
    }
	private String sql;
    public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	private Properties parameterValues;

	private Class entity;
	
	public Class getEntity() {
		return entity;
	}
	public void setEntity(Class entity) {
		this.entity = entity;
	}
	public void addParameterValue(String key ,Object value){
		parameterValues.put(key, value);
	}
	
	
	
	public Properties getParameterValues() {
		return parameterValues;
	}
	public void setParameterValues(Properties parameterValues) {
	
		this.parameterValues = parameterValues;
	}
    
    
}
