package cn.fastmc.sqlconfig.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class SQLConfiguration {
	  protected final Map<String, SqlStatement> statements = new HashMap<String, SqlStatement>();
	  
	  public void addStatements(SqlStatement sm){
		  statements.put(sm.getId(), sm);
	  }
	  
	  public Collection<String> getStatementNames(){
		  return statements.keySet();
	  }
	  
	  public Collection<SqlStatement> getMappedStatements() {
		    return statements.values();
	  }

	  public SqlStatement getMappedStatement(String id) {
		    return statements.get(id);
	  }

}
