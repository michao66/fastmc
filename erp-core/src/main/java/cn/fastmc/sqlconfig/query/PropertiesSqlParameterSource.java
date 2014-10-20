package cn.fastmc.sqlconfig.query;

import java.util.Properties;

import org.springframework.jdbc.core.namedparam.AbstractSqlParameterSource;

public class PropertiesSqlParameterSource extends AbstractSqlParameterSource {
    private Properties sqlParameter;
	public PropertiesSqlParameterSource(Properties sqlParameter){
		this.sqlParameter = sqlParameter;
	}
	@Override
	public Object getValue(String paramName) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return sqlParameter.get(paramName);
	}

	@Override
	public boolean hasValue(String paramName) {
		// TODO Auto-generated method stub
		return sqlParameter.containsKey(paramName);
	}

}
