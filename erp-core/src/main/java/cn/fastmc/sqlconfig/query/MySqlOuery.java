package cn.fastmc.sqlconfig.query;

import java.util.List;
import java.util.Map;

import cn.fastmc.sqlconfig.base.SqlSource;
import cn.fastmc.sqlconfig.base.SqlStatement;
import cn.fastmc.sqlconfig.base.SqlText;

public class MySqlOuery extends JdbcQuery{
	
	private static class MySqlPagingSqlSource implements SqlSource{
		private SqlStatement sqlStatement;

		public MySqlPagingSqlSource(SqlStatement sqlStatement){
			this.sqlStatement = sqlStatement;

		}
		public SqlText getSqlText(Object parameterObject){
			SqlText sqltext = this.sqlStatement.getSqlText(parameterObject);
			sqltext.setSql(builderOraclePagingSql(sqltext.getSql()));
			return sqltext;
		}
		
		private String builderOraclePagingSql(String sql){
			StringBuffer pagingSelect = new StringBuffer( sql.length()+100 );			
		
			pagingSelect.append(sql);
			pagingSelect.append(" limit :start, :limit");
			
			return pagingSelect.toString();
		}
		
	}


	@Override
	public List<?> findPages(String sqlId, Map<String,String> parameterObject,
			int firstResult, int maxResults) {
		SqlStatement sqlstatements = this.getSqlTemplet().find(sqlId);
		MySqlPagingSqlSource sqlsource = new MySqlPagingSqlSource(sqlstatements);
		SqlText sqltext = sqlsource.getSqlText(parameterObject);
		sqltext.addParameterValue("start", firstResult);
		sqltext.addParameterValue("limit", maxResults);
		return this.doQuery(sqltext);
	}

}
