package cn.fastmc.core.jpa.service;


import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.commons.lang.reflect.MethodUtils;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import cn.fastmc.code.jpa.dao.SpringJpaDao;
import cn.fastmc.core.annotation.MetaData;
import cn.fastmc.core.pagination.GroupPropertyFilter;
import cn.fastmc.sqlconfig.query.BaseQuery;

import com.google.common.collect.Lists;

@Transactional
public abstract class BaseService<T extends Persistable<? extends Serializable>, ID extends Serializable> {

    private final Logger logger = LoggerFactory.getLogger(BaseService.class);
    
    private static final String[] DEFAULT_IGNOREPROERTIES = { "id", "createDate"};
    /** 泛型对应的Class定义 */
    protected Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;
    
    /** 子类设置具体的DAO对象实例 */
    abstract protected SpringJpaDao<T, ID> getEntityDao();
    
    @Resource(name="jdbcQuery")
	protected BaseQuery nativeQuery;
    
    @SuppressWarnings("unchecked")
    public BaseService() {
        super();
        // 通过反射取得Entity的Class.
        try {
            Object genericClz = getClass().getGenericSuperclass();
            if (genericClz instanceof ParameterizedType) {
                entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0];
            }
        } catch (Exception e) {
            logger.error("error detail:", e);
        }
    }

    /**
     * 创建数据保存数据之前额外操作回调方法 默认为空逻辑，子类根据需要覆写添加逻辑即可
     * 
     * @param entity
     *            待创建数据对象
     */
    protected void preInsert(T entity) {

    }

    /**
     * 更新数据保存数据之前额外操作回调方法 默认为空逻辑，子类根据需要覆写添加逻辑即可
     * 
     * @param entity
     *            待更新数据对象
     */
    protected void preUpdate(T entity) {

    }

    /**
     * 数据保存操作
     * 
     * @param entity
     * @return
     */
    public T save(T entity) {
        if (entity.isNew()) {
            preInsert(entity);
        } else {
            preUpdate(entity); 
        }
        return getEntityDao().save(entity);
    }
     
    /**
     * 批量数据保存操作 其实现只是简单循环集合每个元素调用 {@link #save(Persistable)}
     * 因此并无实际的Batch批量处理，如果需要数据库底层批量支持请自行实现
     * 
     * @param entities
     *            待批量操作数据集合
     * @return
     */
    public List<T> save(Iterable<T> entities) {
        List<T> result = new ArrayList<T>();
        if (entities == null) {
            return result;
        }
        for (T entity : entities) {
            result.add(save(entity));
        }
        return result;
    }


    /**
     * 基于主键查询单一数据对象
     * 
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public T findOne(ID id) {
        Assert.notNull(id);
        return getEntityDao().findOne(id);
    }



    /**
     * 基于主键集合查询集合数据对象
     * 
     * @param ids
     *            主键集合
     * @return
     */
    @SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
    public List<T> findAll(final Iterable<ID> ids) {
    	 Iterable<T> it = this.getEntityDao().findAll(ids);
         return IteratorUtils.toList(it.iterator());
    }

    /**
     * 数据删除操作
     * 
     * @param entity
     *            待操作数据
     */
    public void delete(T entity) {
        getEntityDao().delete(entity);
    }

    /**
     * 批量数据删除操作 其实现只是简单循环集合每个元素调用 {@link #delete(Persistable)}
     * 因此并无实际的Batch批量处理，如果需要数据库底层批量支持请自行实现
     * 
     * @param entities
     *            待批量操作数据集合
     * @return
     */
    public void delete(Iterable<T> entities) {
        for (T entity : entities) {
            delete(entity);
        }
    }
    
    @Transactional(readOnly = true)
    public Page<T> findByPage( Pageable pageable,GroupPropertyFilter filters) {
        return getEntityDao().findAll( pageable,filters);
    }

	 
    /**
     * 基于动态组合条件对象查询数据集合
     * 
     * @param groupPropertyFilter
     * @return
     */
    @Transactional(readOnly = true)
    public List<T> findByFilters(GroupPropertyFilter filters) {
        return getEntityDao().findAll(filters);
    }

    /**
     * 基于动态组合条件对象和排序定义查询数据集合
     * 
     * @param groupPropertyFilter
     * @param sort
     * @return
   */
    @Transactional(readOnly = true)
    public List<T> findByFilters(GroupPropertyFilter filters, Sort sort) {
 
        return getEntityDao().findAll(filters, sort);
    }

    /**
    * 基于动态组合条件对象和排序定义，限制查询数查询数据集合
    * 主要用于Autocomplete这样的查询避免返回太多数据
    * @param groupPropertyFilter
    * @param sort
    * @return
    */
    @Transactional(readOnly = true)
    public List<T> findByFilters(GroupPropertyFilter filters, Sort sort, int limit) {
        Pageable pageable = new PageRequest(0, limit, sort);
        return findByPage(filters, pageable).getContent();
    }

    /**
     * 基于动态组合条件对象和分页(含排序)对象查询数据集合
     * 
     * @param groupPropertyFilter
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<T> findByPage(GroupPropertyFilter filters, Pageable pageable) {
        return getEntityDao().findAll(pageable,filters);
    }


  
    /**
     * 更新过滤不更新属性
     * @param entity
     * @param ignoreProperties
     * @return
     */
    @Transactional
    public T update(T entity, String... ignoreProperties){
      Assert.notNull(entity);
    
      //if (this.getEntityDao().isManaged(entity))
        //throw new IllegalArgumentException("Entity must not be managed");
      T newObj = findOne(this.getEntityDao().getIdentifier(entity));
      if (newObj != null){    	  
    	copyProperties((Object)entity,(Object)newObj, (String[])ArrayUtils.addAll(ignoreProperties, DEFAULT_IGNOREPROERTIES));
        return this.getEntityDao().save(newObj);
      }
      return this.getEntityDao().save(newObj);
    }
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void copyProperties(Object source, Object target, String[] ignoreProperties){
    	Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(target.getClass());
        List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null &&
					(ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
				PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object sourceValue = readMethod.invoke(source);
						Object targetValue = readMethod.invoke(target, new Object[0]);
						 Object tempValue;
						if ((sourceValue != null) && (targetValue != null) && ((targetValue instanceof Collection))){
							  tempValue = (Collection)targetValue;
			                    ((Collection)tempValue).clear();
			                    ((Collection)tempValue).addAll((Collection)sourceValue);
						}else{
							Method writeMethod = targetPd.getWriteMethod();
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, sourceValue);
		                 }
					}
					catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
    }
    
    /**
     * 根据泛型对象属性和值查询唯一对象
     * 
     * @param property 属性名，即对象中数量变量名称
     * @param value 参数值
     * @return 未查询到返回null，如果查询到多条数据则抛出异常
     */
    public T findByProperty(final String property, final Object value) {
        Specification<T> spec = new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                @SuppressWarnings("rawtypes")
                Path expression = root.get(property);
                return builder.equal(expression, value);
            }
        };

        List<T> entities = this.getEntityDao().findAll(spec);
        if (CollectionUtils.isEmpty(entities)) {
            return null;
        } else {
            Assert.isTrue(entities.size() == 1);
            return entities.get(0);
        }
    }

    /**
     * 根据泛型对象属性和值查询唯一对象
     * 
     * @param property 属性名，即对象中数量变量名称
     * @param value 参数值
     * @return 未查询到返回null，如果查询到多条数据则返回第一条
     */
    public T findFirstByProperty(final String property, final Object value) {
        Specification<T> spec = new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                @SuppressWarnings("rawtypes")
                Path expression = root.get(property);
                return builder.equal(expression, value);
            }
        };

        List<T> entities = this.getEntityDao().findAll(spec);
        if (CollectionUtils.isEmpty(entities)) {
            return null;
        } else {
            return entities.get(0);
        }
    }
    /**
     * 供子类调用的关联对象关联关系操作辅助方法
     * 
     * @param id
     *            当前关联主对象主键，如User对象主键
     * @param r2EntityIds
     *            关联对象的主键集合，如用户关联角色的Role对象集合的主键
     * @param r2PropertyName
     *            主对象中关联集合对象属性的名称，如User对象中定义的userR2Roles属性名
     * @param r2EntityPropertyName
     *            被关联对象在R2关联对象定义中的属性名称，如UserR2Role中定义的role属性名
     * @param op
     *            关联操作类型，如add、del等， @see #R2OperationEnum
     */
    protected void updateRelatedR2s(ID id, Collection<? extends Serializable> r2EntityIds, String r2PropertyName,
            String r2EntityPropertyName, R2OperationEnum op) {
        try {
            T entity = findOne(id);
            List oldR2s = (List) FieldUtils.readDeclaredField(entity, r2PropertyName, true);

            Field r2field = FieldUtils.getField(entityClass, r2PropertyName, true);
            Class r2Class = (Class) (((ParameterizedType) r2field.getGenericType()).getActualTypeArguments()[0]);
            Field entityField = null;
            Field[] fields = r2Class.getDeclaredFields();
            for (Field field : fields) {
                if (field.getType().equals(entityClass)) {
                    entityField = field;
                    break;
                }
            }

            Field r2EntityField = FieldUtils.getField(r2Class, r2EntityPropertyName, true);
            Class r2EntityClass = r2EntityField.getType();

            if (R2OperationEnum.update.equals(op)) {
                if (CollectionUtils.isEmpty(r2EntityIds) && !CollectionUtils.isEmpty(oldR2s)) {
                    oldR2s.clear();
                }
            }

            if (R2OperationEnum.update.equals(op) || R2OperationEnum.add.equals(op)) {
                // 双循环处理需要新增关联的项目
                for (Serializable r2EntityId : r2EntityIds) {
                    Object r2Entity = entityManager.find(r2EntityClass, r2EntityId);
                    boolean tobeAdd = true;
                    for (Object r2 : oldR2s) {
                        if (FieldUtils.readDeclaredField(r2, r2EntityPropertyName, true).equals(r2Entity)) {
                            tobeAdd = false;
                            break;
                        }
                    }
                    if (tobeAdd) {
                        Object newR2 = r2Class.newInstance();
                        FieldUtils.writeDeclaredField(newR2, r2EntityField.getName(), r2Entity, true);
                        FieldUtils.writeDeclaredField(newR2, entityField.getName(), entity, true);
                        oldR2s.add(newR2);
                    }
                }
            }

            if (R2OperationEnum.update.equals(op)) {
                // 双循环处理需要删除关联的项目
                List tobeDleteList = Lists.newArrayList();
                for (Object r2 : oldR2s) {
                    boolean tobeDlete = true;
                    for (Serializable r2EntityId : r2EntityIds) {
                        Object r2Entity = entityManager.find(r2EntityClass, r2EntityId);
                        if (FieldUtils.readDeclaredField(r2, r2EntityPropertyName, true).equals(r2Entity)) {
                            tobeDlete = false;
                            break;
                        }
                    }
                    if (tobeDlete) {
                        tobeDleteList.add(r2);
                    }
                }
                oldR2s.removeAll(tobeDleteList);
            }

            if (R2OperationEnum.delete.equals(op)) {
                // 双循环处理需要删除关联的项目
                List tobeDleteList = Lists.newArrayList();
                for (Object r2 : oldR2s) {
                    boolean tobeDlete = false;
                    for (Serializable r2EntityId : r2EntityIds) {
                        Object r2Entity = entityManager.find(r2EntityClass, r2EntityId);
                        if (FieldUtils.readDeclaredField(r2, r2EntityPropertyName, true).equals(r2Entity)) {
                            tobeDlete = true;
                            break;
                        }
                    }
                    if (tobeDlete) {
                        tobeDleteList.add(r2);
                    }
                }
                oldR2s.removeAll(tobeDleteList);
            }

        } catch (SecurityException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (InstantiationException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
       
 


    /**
     * 供子类调用的关联对象关联关系操作辅助方法
     * 
     * @param id
     *            当前关联主对象主键，如User对象主键
     * @param r2EntityIds
     *            关联对象的主键集合，如用户关联角色的Role对象集合的主键
     * @param r2PropertyName
     *            主对象中关联集合对象属性的名称，如User对象中定义的userR2Roles属性名
     * @param r2EntityPropertyName
     *            被关联对象在R2关联对象定义中的属性名称，如UserR2Role中定义的role属性名
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	protected void updateRelatedR2s(ID id, Serializable[] r2EntityIds, String r2PropertyName,
            String r2EntityPropertyName) {
        try {
            T entity = findOne(id);
            List oldR2s = (List) MethodUtils.invokeExactMethod(entity, "get" + StringUtils.capitalize(r2PropertyName),
                    null);

            if ((r2EntityIds == null || r2EntityIds.length == 0) && !CollectionUtils.isEmpty(oldR2s)) {
                oldR2s.clear();
            } else {
                Field r2field = FieldUtils.getField(entityClass, r2PropertyName, true);
                Class r2Class = (Class) (((ParameterizedType) r2field.getGenericType()).getActualTypeArguments()[0]);
                Field entityField = null;
                Field[] fields = r2Class.getDeclaredFields();
                for (Field field : fields) {
                    if (field.getType().equals(entityClass)) {
                        entityField = field;
                        break;
                    }
                }

                Field r2EntityField = FieldUtils.getField(r2Class, r2EntityPropertyName, true);
                Class r2EntityClass = r2EntityField.getType();

                // 双循环处理需要删除关联的项目
                List tobeDleteList = Lists.newArrayList();
                for (Object r2 : oldR2s) {
                    boolean tobeDlete = true;
                    for (Serializable r2EntityId : r2EntityIds) {
                        Object r2Entity = entityManager.find(r2EntityClass, r2EntityId);
                        if (FieldUtils.readDeclaredField(r2, r2EntityPropertyName, true).equals(r2Entity)) {
                            tobeDlete = false;
                            break;
                        }
                    }
                    if (tobeDlete) {
                        tobeDleteList.add(r2);
                    }
                }
                oldR2s.removeAll(tobeDleteList);

                // 双循环处理需要新增关联的项目
                for (Serializable r2EntityId : r2EntityIds) {
                    Object r2Entity = entityManager.find(r2EntityClass, r2EntityId);
                    boolean tobeAdd = true;
                    for (Object r2 : oldR2s) {
                        if (FieldUtils.readDeclaredField(r2, r2EntityPropertyName, true).equals(r2Entity)) {
                            tobeAdd = false;
                            break;
                        }
                    }
                    if (tobeAdd) {
                        Object newR2 = r2Class.newInstance();
                        FieldUtils.writeDeclaredField(newR2, r2EntityField.getName(), r2Entity, true);
                        FieldUtils.writeDeclaredField(newR2, entityField.getName(), entity, true);
                        oldR2s.add(newR2);
                    }
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    /**
	  * 用于兼容本地SQL配置文件 方法的分页查询
	  * @param pageingSql 分页SQL IDe
	    * @param pagecountSql 查询行数ID
	    * @param queryparam  查询参数
	    * @param pageable 分页行数及排序参数
	    * @return 分页信息
	    */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Page<?> findPageByNativeSql(String pageingSql,String pagecountSql,Map<String,String> queryparam,Pageable pageable){
			Assert.notNull(pageingSql, "pagingSql name must not be null");
			Assert.notNull(pageingSql, "pagingCountSql name must not be null");
			List<?> pageData = (List<?>)nativeQuery.findPages(queryparam.get(pageingSql), queryparam, (pageable.getPageNumber()*pageable.getPageSize()),pageable.getPageSize());
			long pages = nativeQuery.findCount(queryparam.get(pageingSql), queryparam);
			return new PageImpl(pageData,pageable,pages);
	 }
	/**
	 * 用于兼容本地SQL配置的查询
	 * @param sqlId 查询SQL ID
	 * @param queryparam 查询参数
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<?> findListByNativeSql(String sqlId,Map<String,String> queryparam){
		Assert.notNull(sqlId, "sqlId name must not be null");
		List<?> datas = (List<?>)nativeQuery.findList(sqlId, queryparam);
		return datas;
	}
	/**
	 * 用于兼容本地SQL配置的查询(返回规定行数的数据)
	 * @param sqlId 查询SQL ID
	 * @param queryparam 查询参数
	 * @param firstResult 首行
	 * @param maxResults 最大行
	 * @return
	 */
	@Transactional(readOnly = true) 
	public List<?> findListByNativeSql(String sqlId,Map<String,String> queryparam,int firstResult,int maxResults){
		Assert.notNull(sqlId, "sqlId name must not be null");
		List<?> datas = (List<?>)nativeQuery.findPages(sqlId, queryparam, firstResult, maxResults);
		return datas;
	}
	/**
	 * 用于兼容本地SQL配置的查询对象
	 * @param sqlId 查询SQL ID
	 * @param queryparam 查询参数
	 * @return
	 */
    public Object findOneByNativeSql(String sqlId,Map<String,String> queryparam){
    	Assert.notNull(sqlId, "sqlId name must not be null");
    	return nativeQuery.findOne(sqlId, queryparam);
    }
    /**
	 * 用于兼容本地SQL配置的查询行数
	 * @param sqlId 查询SQL ID
	 * @param queryparam 查询参数
	 * @return
	 */
    public long findCountByNativeSql(String sqlId,Map<String,String> queryparam){
    	Assert.notNull(sqlId, "sqlId name must not be null");
    	return nativeQuery.findCount(sqlId, queryparam);
    }
    private class GroupAggregateProperty {
        @MetaData(value = "字面属性")
        private String label;
        @MetaData(value = "JPA表达式")
        private String name;
        @MetaData(value = "JPA表达式alias")
        private String alias;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }
    }

}
