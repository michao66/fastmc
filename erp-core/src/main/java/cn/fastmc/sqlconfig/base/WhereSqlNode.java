package cn.fastmc.sqlconfig.base;



public class WhereSqlNode extends TrimSqlNode {
	public WhereSqlNode( SqlNode contents) {
	    super( contents, "WHERE", "AND |OR ", null, null);
	  }
	
}
