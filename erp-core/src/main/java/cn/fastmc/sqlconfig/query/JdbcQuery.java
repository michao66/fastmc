package cn.fastmc.sqlconfig.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import cn.fastmc.sqlconfig.base.SqlText;

public abstract class JdbcQuery  extends BaseQuery {
	
  

	@Override
	public List<?> findList(String sqlId, Map<String,String> parameterObject) {
		// TODO Auto-generated method stub
		final SqlText sql = getSqlText(sqlId, parameterObject);
    	return doQuery(sql);
	}

	protected List<?> doQuery(final SqlText sql) {
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
    	SqlParameterSource ps = new PropertiesSqlParameterSource(sql.getParameterValues());// 自定义SqlParameterSource满足命名参数查询
		
		List l1 = new ArrayList();
    	if(sql.getEntity()==null){
			 l1 = template.queryForList(sql.getSql(), ps);
		}else{
			l1 = template.query(sql.getSql(), ps,new BeanPropertyRowMapper(sql.getEntity()));
		}
		return l1;
	}

	@Override
	public long findCount(String sqlId, Map<String,String> parameterObject) {
		// TODO Auto-generated method stub
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
		final SqlText sql = getSqlText(sqlId, parameterObject);
		SqlParameterSource ps = new PropertiesSqlParameterSource(sql.getParameterValues());// 自定义SqlParameterSource满足命名参数查询
		return template.queryForInt(sql.getSql(), ps);
	}

	@Override
	public Object findOne(String sqlId, Map<String,String> parameterObject) {
		// TODO Auto-generated method stub
		List<?> list = findList(sqlId,parameterObject);
		if (list != null && !list.isEmpty()) {
			if (list.get(0) == null) {
				return null;
			} else {
				return list.get(0);
			}
		} 
		return null;
	}

	
	private DataSource dataSource;
    
   
  	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}


}
