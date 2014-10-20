package cn.fastmc.core.jasperreports;

import java.util.List;
import java.util.Map;

public interface JRBeanCollectionDataProvide {
   public List<?> getListBean(Map<String,String> parameters);
}
