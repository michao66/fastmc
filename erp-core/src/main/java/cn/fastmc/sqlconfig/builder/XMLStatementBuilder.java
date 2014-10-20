package cn.fastmc.sqlconfig.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.fastmc.core.ServiceException;
import cn.fastmc.core.SqlConfigBuilderException;
import cn.fastmc.core.utils.FunctionUtils;
import cn.fastmc.sqlconfig.base.IfSqlNode;
import cn.fastmc.sqlconfig.base.MixedSqlNode;
import cn.fastmc.sqlconfig.base.SQLConfiguration;
import cn.fastmc.sqlconfig.base.SqlNode;
import cn.fastmc.sqlconfig.base.SqlStatement;
import cn.fastmc.sqlconfig.base.TextSqlNode;
import cn.fastmc.sqlconfig.base.WhereSqlNode;
import cn.fastmc.xml.parsing.XNode;
public class XMLStatementBuilder {
    protected final SQLConfiguration configuration;
    private XmlSqlCofingbuilder  sqlCofingbuilder;
    public XMLStatementBuilder(SQLConfiguration configuration,XmlSqlCofingbuilder  sqlCofingbuilder){
		   this.configuration = configuration;
		   this.sqlCofingbuilder = sqlCofingbuilder;
	}
    
  
  
	public void parseStatementNode(XNode context) throws ServiceException{
		try{
			String id = context.getStringAttribute("id");
			 String sqlType = context.getStringAttribute("sqlType");
			 String entity = context.getStringAttribute("entity");
			 //String parameterType = context.getStringAttribute("parameterType");
			 boolean queryLimit = context.getBooleanAttribute("queryLimit", false);
			 String tableKey = context.getStringAttribute("tableKey");
			 SqlStatement sqlStatement = new SqlStatement();		 
			 sqlStatement.setId(id);
			 sqlStatement.setSqlType(sqlType);
			 sqlStatement.setQueryLimit(queryLimit);
			 sqlStatement.setTableKey(tableKey);
			 if(entity!=null)sqlStatement.setEntity(FunctionUtils.applicationClass(entity));
			 //sqlStatement.setParameterType(ResolveClass.resolveAlias(parameterType));
			 List<SqlNode> contents  = parseDynamicTags(context);
			 MixedSqlNode mixedSqlNodes = new MixedSqlNode(contents);
			 sqlStatement.setRootAqlNode(mixedSqlNodes);
			 configuration.addStatements(sqlStatement);
		}catch(Exception ex){
			throw new ServiceException(ex);
		}
		 
	}
	
	public void parseGlobalSqlNode(XNode context){
		 List<SqlNode> contents = new ArrayList<SqlNode>();
		  NodeList children = context.getNode().getChildNodes();
		  for (int i = 0; i < children.getLength(); i++) {
			   XNode child = context.newXNode(children.item(i));
		  }
	}
	
	private List<SqlNode> parseDynamicTags(XNode context) {
		  List<SqlNode> contents = new ArrayList<SqlNode>();
		  NodeList children = context.getNode().getChildNodes();
		  for (int i = 0; i < children.getLength(); i++) {
		      XNode child = context.newXNode(children.item(i));
		      String nodeName = child.getNode().getNodeName();
		      if (child.getNode().getNodeType() == Node.CDATA_SECTION_NODE
		              || child.getNode().getNodeType() == Node.TEXT_NODE) {
		            String data = child.getStringBody("");
		            contents.add(new TextSqlNode(data));
		      } else {
		          NodeHandler handler = nodeHandlers.get(nodeName);
		          if (handler == null) {
		            throw new SqlConfigBuilderException("Unknown element <" + nodeName + "> in SQL statement.");
		          }
		          handler.handleNode(child, contents);
		        }       
		  }
		 return contents;
	}
	
	 private interface NodeHandler {
		    void handleNode(XNode nodeToHandle, List<SqlNode> targetContents);
	 }
	
	 private Map<String, NodeHandler> nodeHandlers = new HashMap<String, NodeHandler>() {
		    {
		      put("include", new IncludeNodeHandler());
		      put("where", new WhereHandler());
		      put("foreach", new ForEachHandler());
		      put("if", new IfHandler());
		      put("choose", new ChooseHandler());
		      put("when", new IfHandler());
		      put("otherwise", new OtherwiseHandler());		
		    }
		  };
		  
	   private class IncludeNodeHandler implements NodeHandler {
			    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
			      String refid = nodeToHandle.getStringAttribute("refid");
			      XNode includeNode = sqlCofingbuilder.getSqlFragment(refid);
			      if (includeNode == null) {
			      
			        if (includeNode == null) {
			          throw new SqlConfigBuilderException("Could not find SQL statement to include with refid '" + refid + "'");
			        }
			      }
			      MixedSqlNode mixedSqlNode = new MixedSqlNode(contents(includeNode));
			      targetContents.add(mixedSqlNode);
			  }	
	  }
	   
	   private List<SqlNode> contents(XNode includeNode) {
		      return parseDynamicTags(includeNode);
	   }
	   
	   private class WhereHandler implements NodeHandler {
		    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
		    	  List<SqlNode> contents = parseDynamicTags(nodeToHandle);
		          MixedSqlNode mixedSqlNode = new MixedSqlNode(contents);
		          WhereSqlNode where = new WhereSqlNode(mixedSqlNode);
		          targetContents.add(where);
		  }	
       } 
	   
	   private class ForEachHandler implements NodeHandler {
		    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
		      String refid = nodeToHandle.getStringAttribute("refid");

		  }	
      } 
	   
	   private class IfHandler implements NodeHandler {
		    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
		      List<SqlNode> contents = parseDynamicTags(nodeToHandle);	
		      MixedSqlNode mixedSqlNodes = new MixedSqlNode(contents);
		      //String JdbcType = nodeToHandle.getStringAttribute("JdbcType");
		      String test = nodeToHandle.getStringAttribute("test");
		      IfSqlNode ifSqlNode = new IfSqlNode(mixedSqlNodes, test);
		      targetContents.add(ifSqlNode);
		  }	
      } 
	   
	   private class ChooseHandler implements NodeHandler {
		    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
		      String refid = nodeToHandle.getStringAttribute("refid");

		  }	
       } 
	   
	   private class OtherwiseHandler implements NodeHandler {
		    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
		      String refid = nodeToHandle.getStringAttribute("refid");

		  }	
      } 
	   

	   


}
