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
package cn.fastmc.core.jpa.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.fastmc.core.pagination.GroupPropertyFilter;
import cn.fastmc.core.pagination.PropertyFilter;
import cn.fastmc.core.pagination.PropertyFilter.MatchType;

/**
 * 针对spring data jpa所提供的接口{@link JpaRepository}再次扩展，支持{@link PropertyFilter}查询和其他方式查询
 * @author maurice
 *
 * @param <T> ORM对象
 * @param <ID> 主键Id类型
 */
public interface BasicJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>{
	
	/**
	 * 通过属性过滤器查询全部对象
	 * 
	 * @param filters 属性过滤器集合
	 * 
	 * @return List
	 */
	public List<T> findAll(GroupPropertyFilter filters);
	
	/**
	 * 通过属性名查询全部对象
	 * 
	 * @param propertyName 属性名
	 * @param value 值
	 * 
	 * @return List
	 */
	public List<T> findAll(String propertyName,Object value);
	
	/**
	 * 通过属性名查询全部对象
	 * 
	 * @param propertyName 属性名
	 * @param value 值
	 * @param sort 排序方法
	 * 
	 * @return List
	 */
	public List<T> findAll(String propertyName,Object value,Sort sort);
	
	/**
	 * 通过属性名查询全部对象
	 * 
	 * @param propertyName 属性名
	 * @param value 值
	 * @param restrictionName 约束条件名称
	 * 
	 * @return List
	 */
	public List<T> findAll(String propertyName,Object value,MatchType restrictionName);
	
	/**
	 * 通过属性名查询全部对象
	 * 
	 * @param propertyName 属性名
	 * @param value 值
	 * @param sort 排序方法
	 * @param restrictionName 约束条件名称
	 * 
	 * @return List
	 */
	public List<T> findAll(String propertyName,Object value,Sort sort,MatchType restrictionName);
	
	/**
	 * 通过属性过滤器查询全部对象
	 * 
	 * @param filters 属性过滤器集合
	 * @param sort 排序形式
	 * 
	 * @return List
	 */
	public List<T> findAll(GroupPropertyFilter filters, Sort sort);
	
	/**
	 * 通过属性过滤器查询对象分页
	 * 
	 * @param pageable 分页参数
	 * @param filters 属性过滤器集合
	 * 
	 * @return {@link Page}
	 */
	public Page<T> findAll(Pageable pageable, GroupPropertyFilter filters);
	
	/**
	 * 通过属性过滤器查询单个对象
	 * 
	 * @param filters 属性过滤器
	 * 
	 * @return Object
	 */
	public T findOne(GroupPropertyFilter filters);
	
	/**
	 * 通过属性名查询单个对象
	 * 
	 * @param propertyName 属性名
	 * @param value 值
	 * 
	 * @return Object
	 */
	public T findOne(String propertyName,Object value);
	
	/**
	 * 通过属性名查询全部对象
	 * 
	 * @param propertyName 属性名
	 * @param value 值
	 * @param restrictionName 约束条件名称
	 * 
	 * @return Object
	 */
	public T findOne(String propertyName,Object value,MatchType restrictionName);
	
	// 扩展提供 SPRING　Jpa 基础函数，以供service层更灵活使用
	
	
	  
	public ID getIdentifier(T entity);
	 
	  
	public boolean isManaged(T entity);
	  
   
	
	public void batchInsert(List<T> list);  
    
    public void batchUpdate(List<T> list);
	 
}
