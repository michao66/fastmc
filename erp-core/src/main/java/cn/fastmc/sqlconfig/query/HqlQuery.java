/**
 * 
 * @author mi
 */
package cn.fastmc.sqlconfig.query;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import cn.fastmc.sqlconfig.base.MixedSqlNode;
import cn.fastmc.sqlconfig.base.SqlStatement;
import cn.fastmc.sqlconfig.base.SqlText;
import cn.fastmc.sqlconfig.base.TextSqlNode;

public class HqlQuery extends BaseQuery {
   
    private HibernateTemplate hibernateTemplate;
    public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<?> findPages(String sqlId,Map<String,String> parameterObject,
			final int firstResult,final int maxResults) {
		// TODO Auto-generated method stub
		Object mapObj = null;
		Map parameterMap = null;;
		SqlStatement sqlStatement = getSqlStatement(sqlId, parameterObject);
		if(parameterObject instanceof Map){
			 mapObj = ((Map)parameterObject).get("queryLimit");
			 parameterMap = (Map)parameterObject;
		}
		
		if(sqlStatement.isQueryLimit()&&mapObj!=null&&parameterMap!=null){
			String key = sqlStatement.getTableKey();
			MixedSqlNode mixSqlNode = (MixedSqlNode)sqlStatement.getRootAqlNode();
			String[] queryLimitMap = (String[])mapObj;
			parameterMap.put("viewRange", Arrays.asList(queryLimitMap));
			TextSqlNode sqlText = new TextSqlNode("and "+key+" in (#{viewRange:List})");
			mixSqlNode.addSqlNode(sqlText);
		}
		final SqlText sqlText = sqlStatement.getSqlText(parameterObject);
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session s)throws HibernateException, SQLException {	
				Query query = setHqlParamValue(sqlText, s);
				query.setFirstResult(firstResult);
				query.setMaxResults(maxResults);
				List list = query.list();
				return list;
			}

		});
	}

	@Override
	public List<?> findList(String sqlId, Map<String,String> parameterObject) {
		// TODO Auto-generated method stub
		final SqlText sql = getSqlText(sqlId, parameterObject);
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session s)throws HibernateException, SQLException {
				Query query = setHqlParamValue(sql, s);
				List list = query.list();
				return list;
			}
		});
	}

	@Override
	public long findCount(String sqlId, Map<String,String> parameterObject) {
		// TODO Auto-generated method stub
		List l2 = findList( sqlId, parameterObject);
		long num = 0;
		if (l2 != null && !l2.isEmpty()) {
			if (l2.get(0) == null) {
				num = 0;
			} else {
				num = (Long) l2.get(0);
			}
		} else {
			num = 0;
		}
		return num;
	}

	@Override
	public Object findOne(String sqlId, Map<String,String> parameterObject) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * 设置查询参数
	 * @param sql 需设置SQL
	 * @param s 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Query setHqlParamValue(final SqlText sql, Session s) {
		Query query = s.createQuery(sql.getSql());
		if (	sql.getParameterValues() != null) {
		    Set names = sql.getParameterValues().keySet();
			for (Iterator it = names.iterator(); it.hasNext();) {
				String key = (String)it.next();
				Object value = sql.getParameterValues().get(key);
				if( value instanceof Collection){
					query.setParameterList(key, (Collection)value);
				}else{
					query.setParameter(key,value);
				}
			}
		}
		return query;
	}
}
