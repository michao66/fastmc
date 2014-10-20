package cn.fastmc.sqlconfig.base;

import cn.fastmc.sqlconfig.builder.SqlSourceBuilder;

public class SqlStatement implements SqlSource {
	private String id;
	private String sqlType;
	private Class<?> entity;
	private String parameterType;
	private boolean queryLimit;
	private String tableKey;

	public boolean isQueryLimit() {
		return queryLimit;
	}

	public void setQueryLimit(boolean queryLimit) {
		this.queryLimit = queryLimit;
	}

	public String getTableKey() {
		return tableKey;
	}

	public void setTableKey(String tableKey) {
		this.tableKey = tableKey;
	}

	public String getParameterType() {
		return parameterType;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	private SqlNode rootAqlNode;

	public SqlNode getRootAqlNode() {
		return rootAqlNode;
	}

	public void setRootAqlNode(SqlNode rootAqlNode) {
		this.rootAqlNode = rootAqlNode;
	}

	public String getSqlType() {
		return sqlType;
	}

	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

	public Class<?> getEntity() {
		return entity;
	}

	public void setEntity(Class<?> entity) {
		this.entity = entity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SqlText getSqlText(Object parameterObject) {
		DynamicContext context = new DynamicContext(parameterObject);
		rootAqlNode.apply(context);
		SqlSourceBuilder builder = new SqlSourceBuilder();
		SqlText sql = builder.parse(context.getSql(), parameterType,parameterObject);
		sql.setEntity(entity);
		return sql;
	}
}
