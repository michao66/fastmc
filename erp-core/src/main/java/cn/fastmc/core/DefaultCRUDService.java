package cn.fastmc.core;

import java.io.Serializable;
import java.util.Map;


public interface DefaultCRUDService {

	public abstract void deleteById( String bean, Map<String, String> param);

	public abstract void saveOrUpdate(Object entity);

	public abstract Object getBeanById(Class<?> clazz, Serializable id);

	public abstract Object getBeanById(String beanName, String findId);

}