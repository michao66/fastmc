package cn.fastmc.sqlconfig.query;

import java.util.List;
import java.util.Map;

public interface Iquery {
       public List<?> findPages(String sqlId,Map<String,String> parameterObject, int firstResult, int maxResults);
       public List<?> findList(String sqlId,Map<String,String> parameterObject);
       public long findCount(String sqlId,Map<String,String> parameterObject);
       public Object findOne(String sqlId,Map<String,String> parameterObject);
       
}
