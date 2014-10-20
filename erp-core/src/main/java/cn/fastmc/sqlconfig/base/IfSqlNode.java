package cn.fastmc.sqlconfig.base;

public class IfSqlNode implements SqlNode {   
   private String test;
   private SqlNode contents;
   private ExpressionEvaluator evaluator;
   
   public IfSqlNode(SqlNode contents, String test) {
	    this.evaluator = new ExpressionEvaluator();
	    this.test = test;
	    this.contents = contents;

   }
   
   public boolean apply(DynamicContext context) {
	    if (evaluator.evaluateBoolean(test, context.getParameterObject())) {
	      contents.apply(context);
	      return true;
	     }
	    return false;
	  }
}
