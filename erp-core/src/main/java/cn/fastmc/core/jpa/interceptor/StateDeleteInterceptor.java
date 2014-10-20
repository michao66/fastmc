package cn.fastmc.core.jpa.interceptor;

import java.io.Serializable;

import cn.fastmc.core.annotation.StateDelete;
import cn.fastmc.core.jpa.repository.support.JpaSupportRepository;
import cn.fastmc.core.utils.ReflectionUtils;

/**
 * 状态删除拦截器
 * 
 * @author maurice
 *
 * @param <E> 持久化对象类型
 * @param <ID> id主键类型
 */
public class StateDeleteInterceptor<E,ID extends Serializable> implements OrmDeleteInterceptor<E, JpaSupportRepository<E,ID>>{

	/*
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.interceptor.OrmDeleteInterceptor#onDelete(java.io.Serializable, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean onDelete(Serializable id, E entity,JpaSupportRepository<E, ID> persistentContext) {
		
		Class<?> entityClass = ReflectionUtils.getTargetClass(entity);
		StateDelete stateDelete = ReflectionUtils.getAnnotation(entityClass,StateDelete.class);
		if (stateDelete == null) {
			return Boolean.TRUE;
		}
		
		Object value = null;//ConvertUtils.convertToObject(stateDelete.value(), stateDelete.type().getValue());
		ReflectionUtils.invokeSetterMethod(entity, stateDelete.propertyName(), value);
		persistentContext.save(entity);
		
		return Boolean.FALSE;
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.interceptor.OrmDeleteInterceptor#onPostDelete(java.io.Serializable, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void onPostDelete(Serializable id, E entity,JpaSupportRepository<E, ID> persistentContext) {
		
	}

}
