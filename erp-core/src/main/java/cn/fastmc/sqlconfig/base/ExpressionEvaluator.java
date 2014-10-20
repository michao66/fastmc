package cn.fastmc.sqlconfig.base;

import java.math.BigDecimal;
import java.util.Arrays;

import ognl.Ognl;
import ognl.OgnlException;
import cn.fastmc.core.SqlConfigBuilderException;

public class ExpressionEvaluator {
	
	   public boolean evaluateBoolean(String expression, Object parameterObject) {
		    try {
		      Object value = Ognl.getValue(expression, parameterObject);
		      if (value instanceof Boolean) return (Boolean) value;
		      if (value instanceof Number) return !new BigDecimal(String.valueOf(value)).equals(BigDecimal.ZERO);
		      return value != null;
		    } catch (OgnlException e) {
		      throw new SqlConfigBuilderException("Error evaluating expression '" + expression + "'. Cause: " + e, e);
		    }
		  }

		  public Iterable evaluateIterable(String expression, Object parameterObject) {
		    try {
		      Object value = Ognl.getValue(expression, parameterObject);
		      if (value == null) throw new SqlConfigBuilderException("The expression '" + expression + "' evaluated to a null value.");
		      if (value instanceof Iterable) return (Iterable) value;
		      if (value.getClass().isArray()) return Arrays.asList((Object[]) value);
		      throw new SqlConfigBuilderException("Error evaluating expression '" + expression + "'.  Return value (" + value + ") was not iterable.");
		    } catch (OgnlException e) {
		      throw new SqlConfigBuilderException("Error evaluating expression '" + expression + "'. Cause: " + e, e);
		    }
		  }

}
