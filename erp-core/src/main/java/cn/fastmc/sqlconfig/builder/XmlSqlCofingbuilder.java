package cn.fastmc.sqlconfig.builder;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fastmc.core.Resources;
import cn.fastmc.sqlconfig.base.SQLConfiguration;
import cn.fastmc.xml.parsing.XNode;
import cn.fastmc.xml.parsing.XPathParser;

public final class XmlSqlCofingbuilder {
    private SQLConfiguration configuration;
	private XPathParser parser;
	private Map<String, XNode> sqlFragments = new HashMap<String, XNode>();
	
	 public XmlSqlCofingbuilder(Reader reader,SQLConfiguration configuration) {				   
		    this.parser = new XPathParser(reader, false);		 
		    this.configuration = configuration;
     }
	 
	 public XNode getSqlFragment(String key){
	    	return this.sqlFragments.get(key);
	 }
	    
	 public void parse() { 
		 configurationElement(parser.evalNode("/sqls"));
	 }
	 
	 private void configurationElement(XNode context) {
		 try {
			 globalsqlElement(context.evalNodes("globalsql"));	
			 buildStatementFromContext(context.evalNodes("select|insert|update|delete"));	
		  } catch (Exception e) {
		        throw new RuntimeException("Error parsing Mapper XML. Cause: " + e, e);
		  }

	}
	 
	private void  globalsqlElement(List<XNode> list){
		  for (XNode globalsqlNode : list) {
			  String id = globalsqlNode.getStringAttribute("id");
			  sqlFragments.put(id, globalsqlNode);	
		  }
	}
	 
	 private void buildStatementFromContext(List<XNode> list) {
		    for (XNode context : list) {  
		      final XMLStatementBuilder statementParser = new XMLStatementBuilder(configuration,this);
		      statementParser.parseStatementNode(context);		    
		    }
		  }
	 
	 public static void main(String[] args){
		 try{
			 Map<String, XNode> sqlFragments = new HashMap<String, XNode>();
			 Reader  r =  Resources.getResourceAsReader("NewFile.xml"); 
			 //XmlSqlCofingbuilder xml = new XmlSqlCofingbuilder(r,sqlFragments);
			 //xml.parse();
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }
		 
	 }

}
