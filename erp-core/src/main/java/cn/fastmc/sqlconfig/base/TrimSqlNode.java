/**
 * 用于整理SQL语句 替代还有的 where 1=1的组合方式
 * @author mi
 */
package cn.fastmc.sqlconfig.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;


public class TrimSqlNode implements SqlNode {
	private SqlNode contents;
	private String prefix;
	private String suffix;
	private List<String> prefixesToOverride = new ArrayList<String>();
	private List<String> suffixesToOverride = new ArrayList<String>();
    
	/**
	 * 
	 * @param contents
	 *            SQL配置元素
	 * @param prefix
	 *            组合前置字符串
	 * @param prefixesToOverride
	 *            前置截取标识
	 * @param suffix
	 *            组合后置字符串
	 * @param suffixesToOverride
	 *            后置截取标识
	 */
	public TrimSqlNode(SqlNode contents, String prefix,
			String prefixesToOverride, String suffix, String suffixesToOverride) {
		this.contents = contents;
		this.prefix = prefix;
		this.prefixesToOverride = parseOverrides(prefixesToOverride);
		this.suffix = suffix;
		this.suffixesToOverride = parseOverrides(suffixesToOverride);
	}

	public boolean apply(DynamicContext context) {
		FilteredDynamicContext filteredDynamicContext = new FilteredDynamicContext(
				context);
		boolean result = contents.apply(filteredDynamicContext);
		filteredDynamicContext.applyAll();
		return result;
	}

	/**
	 * 用于截取的字符串标识
	 * 
	 * @param overrides
	 * @return
	 */
	private List<String> parseOverrides(String overrides) {
		if (overrides != null) {
			final StringTokenizer parser = new StringTokenizer(overrides, "|",	false);
			return new ArrayList<String>() {
				{
					while (parser.hasMoreTokens()) {
						add(parser.nextToken().toUpperCase());
					}
				}
			};
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * 处理截取代理类
	 * 
	 * 
	 */
	private class FilteredDynamicContext extends DynamicContext {
		private DynamicContext delegate;
		private boolean prefixApplied;
		private boolean suffixApplied;
		private StringBuilder sqlBuffer;

		public FilteredDynamicContext(DynamicContext delegate) {
			super(delegate.getParameterObject());
			this.delegate = delegate;
			this.prefixApplied = false;
			this.suffixApplied = false;
			this.sqlBuffer = new StringBuilder();
		}

		public void applyAll() {
			sqlBuffer = new StringBuilder(sqlBuffer.toString().trim());
			String trimmedUppercaseSql = sqlBuffer.toString().toUpperCase();
			if (trimmedUppercaseSql.length() > 0) {
				applyPrefix(sqlBuffer, trimmedUppercaseSql);
				applySuffix(sqlBuffer, trimmedUppercaseSql);
			}
			delegate.appendSql(sqlBuffer.toString());
		}

		public void appendSql(String sql) {
			sqlBuffer.append(sql);
		}

		public String getSql() {
			return delegate.getSql();
		}

		private void applyPrefix(StringBuilder sql, String trimmedUppercaseSql) {
			if (!prefixApplied) {
				prefixApplied = true;
				for (String toRemove : prefixesToOverride) {
					if (trimmedUppercaseSql.startsWith(toRemove)
							|| trimmedUppercaseSql.startsWith(toRemove.trim())) {
						sql.delete(0, toRemove.trim().length());
						break;
					}
				}
				if (prefix != null) {
					sql.insert(0, " ");
					sql.insert(0, prefix);
				}
			}
		}

		private void applySuffix(StringBuilder sql, String trimmedUppercaseSql) {
			if (!suffixApplied) {
				suffixApplied = true;
				for (String toRemove : suffixesToOverride) {
					if (trimmedUppercaseSql.endsWith(toRemove)
							|| trimmedUppercaseSql.endsWith(toRemove.trim())) {
						int start = sql.length() - toRemove.trim().length();
						int end = sql.length();
						sql.delete(start, end);
						break;
					}
				}
				if (suffix != null) {
					sql.append(" ");
					sql.append(suffix);
				}
			}
		}
	}
	
    public SqlNode getSqlNode(){
    	return contents;
    }

}
