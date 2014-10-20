package cn.fastmc.sqlconfig.query;

import cn.fastmc.sqlconfig.base.SqlStatement;
import cn.fastmc.sqlconfig.base.SqlText;
import cn.fastmc.sqlconfig.builder.SqlTempletBuilder;

public abstract class BaseQuery implements Iquery {
	private SqlTempletBuilder sqlTemplet;
	
	public SqlTempletBuilder getSqlTemplet() {
		return sqlTemplet;
	}

	public void setSqlTemplet(SqlTempletBuilder sqlTemplet) {
		this.sqlTemplet = sqlTemplet;
	}
	
	public BaseQuery() {
		
	}

	protected SqlText getSqlText(String sqlId, Object parameterObject) {
		SqlStatement sqlstatements = sqlTemplet.find(sqlId);
		final SqlText sql = sqlstatements.getSqlText(parameterObject);
		return sql;
	}
	
	protected SqlStatement getSqlStatement(String sqlId, Object parameterObject) {
		SqlStatement sqlstatements = sqlTemplet.find(sqlId);
		return sqlstatements;
	}

}