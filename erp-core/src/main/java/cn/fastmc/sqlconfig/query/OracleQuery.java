package cn.fastmc.sqlconfig.query;

import java.util.List;
import java.util.Map;

import cn.fastmc.sqlconfig.base.SqlSource;
import cn.fastmc.sqlconfig.base.SqlStatement;
import cn.fastmc.sqlconfig.base.SqlText;

public class OracleQuery extends JdbcQuery {

	private static class OraclePagingSqlSource implements SqlSource{
		private SqlStatement sqlStatement;

		public OraclePagingSqlSource(SqlStatement sqlStatement){
			this.sqlStatement = sqlStatement;

		}
		public SqlText getSqlText(Object parameterObject){
			SqlText sqltext = this.sqlStatement.getSqlText(parameterObject);
			sqltext.setSql(builderOraclePagingSql(sqltext.getSql()));
			return sqltext;
		}
		
		private String builderOraclePagingSql(String sql){
			StringBuffer pagingSelect = new StringBuffer( sql.length()+100 );			
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
			pagingSelect.append(sql);
			pagingSelect.append(" ) row_ where rownum <= :end) where rownum_ > :start");
			
			return pagingSelect.toString();
		}
		
	}


	@Override
	public List<?> findPages(String sqlId, Map<String,String> parameterObject,
			int firstResult, int maxResults) {
		SqlStatement sqlstatements = this.getSqlTemplet().find(sqlId);
		OraclePagingSqlSource sqlsource = new OraclePagingSqlSource(sqlstatements);
		SqlText sqltext = sqlsource.getSqlText(parameterObject);
		sqltext.addParameterValue("start", firstResult);
		sqltext.addParameterValue("end", firstResult+maxResults);
		return this.doQuery(sqltext);
	}

}
