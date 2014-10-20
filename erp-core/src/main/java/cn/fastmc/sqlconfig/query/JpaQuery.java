package cn.fastmc.sqlconfig.query;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cn.fastmc.sqlconfig.base.SqlStatement;
import cn.fastmc.sqlconfig.base.SqlText;


public class JpaQuery extends BaseQuery {
	@PersistenceContext
    protected EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public  List<?>  findPages(String sqlId,Map<String,String> parameterObject,
			final int firstResult,final int maxResults) {
		// TODO Auto-generated method stub			
		SqlStatement sqlStatement = getSqlStatement(sqlId, parameterObject);
		final SqlText sqlText = sqlStatement.getSqlText(parameterObject);
		Query query = setHqlParamValue(sqlText);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.getResultList();

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public  List<?> findList(String sqlId, Map<String,String> parameterObject) {
		// TODO Auto-generated method stub
		final SqlText sqlText = getSqlText(sqlId, parameterObject);
		Query query = setHqlParamValue(sqlText);
		return query.getResultList();
	}

	@Override
	public long findCount(String sqlId, Map<String,String> parameterObject) {
		// TODO Auto-generated method stub
		SqlStatement sqlStatement = getSqlStatement(sqlId, parameterObject);
		final SqlText sqlText = sqlStatement.getSqlText(parameterObject);
		Query query = setHqlParamValue(sqlText);
		Long result = (Long)query.setFlushMode(FlushModeType.COMMIT).getSingleResult();
		return result.longValue();
	}

	@Override
	public Object findOne(String sqlId,Map<String,String> parameterObject) {
		SqlStatement sqlStatement = getSqlStatement(sqlId, parameterObject);
		final SqlText sqlText = sqlStatement.getSqlText(parameterObject);
		Query query = setHqlParamValue(sqlText);
		return query.setFlushMode(FlushModeType.COMMIT).getSingleResult();
	
	}
	
	/**
	 * 设置查询参数
	 * @param sql 需设置SQL
	 * @param s 
	 * @return
	 */
    @SuppressWarnings("rawtypes")
	private Query setHqlParamValue(final SqlText sql) {
		Query query = entityManager.createQuery(sql.getSql());
		if (sql.getParameterValues() != null) {
		 
			Set names = sql.getParameterValues().keySet();
			for (Iterator it = names.iterator(); it.hasNext();) {
				String key = (String)it.next();
				Object value = sql.getParameterValues().get(key);
				query.setParameter(key,value);
			}
		}
		return query;
	}

}
