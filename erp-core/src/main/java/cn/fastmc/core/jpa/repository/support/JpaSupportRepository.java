/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.fastmc.core.jpa.repository.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import cn.fastmc.core.jpa.interceptor.OrmDeleteInterceptor;
import cn.fastmc.core.jpa.interceptor.OrmSaveInterceptor;
import cn.fastmc.core.jpa.repository.BasicJpaRepository;
import cn.fastmc.core.jpa.specification.Specifications;
import cn.fastmc.core.pagination.GroupPropertyFilter;
import cn.fastmc.core.pagination.PropertyFilter;
import cn.fastmc.core.pagination.PropertyFilter.MatchType;
import cn.fastmc.core.utils.ReflectionUtils;

/**
 * {@link BasicJpaRepository}接口实现类，并在{@link SimpleJpaRepository}基础上扩展,包含对{@link PropertyFilter}的支持。或其他查询的支持,
 * 重写了{@link SimpleJpaRepository#save(Object)}和{@link SimpleJpaRepository#delete(Object)}方法，支持@StateDelete注解和@ConvertProperty注解
 * 
 * @author maurice
 *
 * @param <T> ORM对象
 * @param <ID> 主键Id类型
 */
@SuppressWarnings("unchecked")
public class JpaSupportRepository<T, ID extends Serializable>  extends SimpleJpaRepository<T, ID> implements BasicJpaRepository<T, ID>{
	
	

	@Override
	@Transactional
	public void batchInsert(List<T> list) {
		// TODO Auto-generated method stub
		 for(int i = 0; i < list.size(); i++) {    
			 save(list.get(i));
	        }  
	}

	@Override
	@Transactional
	public void batchUpdate(List<T> list) {
		// TODO Auto-generated method stub
		 for(int i = 0; i < list.size(); i++) {  
			 entityManager.merge(list.get(i));  
	            if(i % 30== 0) {  
	            	entityManager.flush();  
	            	entityManager.clear();  
	            }  
	        } 
	}


	private EntityManager entityManager;
	private JpaEntityInformation<T, ?> entityInformation;
	
	//当删除对象时的拦截器
	private List<OrmDeleteInterceptor<T, JpaSupportRepository<T, ID>>> deleteInterceptors;
	//当保存或更新对象时的拦截器
	private List<OrmSaveInterceptor<T, JpaSupportRepository<T, ID>>> saveInterceptors;
	
	public JpaSupportRepository(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		this.entityManager = entityManager;
		installInterceptors();
	}
	
	public JpaSupportRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
		super(entityInformation, em);
		this.entityInformation = entityInformation;
		this.entityManager = em;
		installInterceptors();
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public JpaEntityInformation<T, ?> getEntityInformation() {
		return entityInformation;
	}
	
	public List<OrmDeleteInterceptor<T, JpaSupportRepository<T, ID>>> getDeleteInterceptors() {
		return deleteInterceptors;
	}

	public List<OrmSaveInterceptor<T, JpaSupportRepository<T, ID>>> getSaveInterceptors() {
		return saveInterceptors;
	}

	/**
	 * 初始化所有拦截器
	 */
	private void installInterceptors() {
		
		//----初始化删除需要的所有拦截器----//
		deleteInterceptors = new ArrayList<OrmDeleteInterceptor<T,JpaSupportRepository<T,ID>>>();
		//deleteInterceptors.add(new StateDeleteInterceptor<T, ID>());
		//deleteInterceptors.add(new TreeEntityInterceptor<T, ID>());
		
		//----初始化保存或更新需要的所有拦截器----//
		saveInterceptors = new ArrayList<OrmSaveInterceptor<T,JpaSupportRepository<T,ID>>>();
		//saveInterceptors.add(new TreeEntityInterceptor<T, ID>());
		//saveInterceptors.add(new SecurityCodeInterceptor<T, ID>());
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#save(S)
	 */
	@Override
	@Transactional
	public <S extends T> S save(S entity) {
		
		for (OrmSaveInterceptor<T,JpaSupportRepository<T, ID>> interceptor : saveInterceptors) {
			ID id = ReflectionUtils.invokeGetterMethod(entity, getIdName());
			if (!interceptor.onSave(entity, this, id)) {
				return null;
			}
		}
		
		S result = null;
		
		if (entityInformation.isNew(entity)) {
			entityManager.persist(entity);
			result = entity;
		} else {
			result = entityManager.merge(entity);
		}
		
		for (OrmSaveInterceptor<T,JpaSupportRepository<T, ID>> interceptor : saveInterceptors) {
			ID id = ReflectionUtils.invokeGetterMethod(entity, getIdName());
			interceptor.onPostSave(entity, this, id);
		}
		
		return result;
	}
	
	 /*
	  * (non-Javadoc)
	  * @see org.springframework.data.jpa.repository.support.SimpleJpaRepository#delete(java.lang.Object)
	  */
	@Override
	@Transactional
	public void delete(T entity) {
		for (OrmDeleteInterceptor<T,JpaSupportRepository<T, ID>> interceptor : deleteInterceptors) {
			ID id = ReflectionUtils.invokeGetterMethod(entity, getIdName());
			if (!interceptor.onDelete(id, entity, this)) {
				return ;
			}
		}
		
		super.delete(entity);
		
		for (OrmDeleteInterceptor<T,JpaSupportRepository<T, ID>> interceptor : deleteInterceptors) {
			ID id = ReflectionUtils.invokeGetterMethod(entity, getIdName());
			interceptor.onPostDelete(id, entity, this);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.core.spring.data.jpa.repository.BasicJpaRepository#findAll(java.util.List)
	 */
	@Override
	public List<T> findAll(GroupPropertyFilter filters) {
		return findAll(filters,(Sort)null);
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.core.spring.data.jpa.repository.BasicJpaRepository#findAll(java.util.List, org.springframework.data.domain.Sort)
	 */
	@Override
	public List<T> findAll(GroupPropertyFilter filters, Sort sort) {
		
		return findAll(Specifications.get(filters), sort);
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.core.spring.data.jpa.repository.BasicJpaRepository#findAll(org.springframework.data.domain.Pageable, java.util.List)
	 */
	@Override
	public Page<T> findAll(Pageable pageable, GroupPropertyFilter filters) {
		
		return findAll(Specifications.get(filters),pageable);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.core.spring.data.jpa.repository.BasicJpaRepository#findAll(java.lang.String, java.lang.Object)
	 */
	@Override
	public List<T> findAll(String propertyName, Object value) {
		
		return findAll(propertyName, value, (Sort)null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.core.spring.data.jpa.repository.BasicJpaRepository#findAll(java.lang.String, java.lang.Object, org.springframework.data.domain.Sort)
	 */
	@Override
	public List<T> findAll(String propertyName, Object value, Sort sort) {
		return findAll(propertyName, value, sort, MatchType.EQ);
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.core.spring.data.jpa.repository.BasicJpaRepository#findAll(java.lang.String, java.lang.Object, java.lang.String)
	 */
	@Override
	public List<T> findAll(String propertyName, Object value,MatchType restrictionName) {
		return findAll(propertyName, value, (Sort)null, restrictionName);
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.core.spring.data.jpa.repository.BasicJpaRepository#findAll(java.lang.String, java.lang.Object, org.springframework.data.domain.Sort, java.lang.String)
	 */
	@Override
	public List<T> findAll(String propertyName, Object value, Sort sort,MatchType restrictionName) {
		
		return findAll(Specifications.get(propertyName, value, restrictionName),sort);
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.core.spring.data.jpa.repository.BasicJpaRepository#findOne(java.util.List)
	 */
	@Override
	public T findOne(GroupPropertyFilter filters) {
		return findOne(Specifications.get(filters));
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.core.spring.data.jpa.repository.BasicJpaRepository#findOne(java.lang.String, java.lang.Object)
	 */
	@Override
	public T findOne(String propertyName, Object value) {
		return findOne(propertyName, value, MatchType.EQ);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.github.dactiv.orm.core.spring.data.jpa.repository.BasicJpaRepository#findOne(java.lang.String, java.lang.Object, java.lang.String)
	 */
	@Override
	public T findOne(String propertyName, Object value, MatchType restrictionName) {
		return findOne(Specifications.get(propertyName, value, restrictionName));
	}

	public String getEntityName() {
		return entityInformation.getEntityName();
	}

	@SuppressWarnings("rawtypes")
	public String getIdName() {
		JpaMetamodelEntityInformation information = (JpaMetamodelEntityInformation) entityInformation;
		return information.getIdAttributeNames().iterator().next().toString();
		
	}
	


	@Override
	public ID getIdentifier(T entity) {
		 Assert.notNull(entity);
		 return (ID)this.entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity);
	}

	@Override
	public boolean isManaged(T entity) {
		return this.entityManager.contains(entity);
	}

	
	
}
