package com.slsoft.core.action;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import cn.fastmc.core.ResultMessage;
import cn.fastmc.core.ServiceException;
import cn.fastmc.core.annotation.MetaData;
import cn.fastmc.core.jpa.entity.PersistableEntity;
import cn.fastmc.core.jpa.service.BaseService;
import cn.fastmc.core.pagination.GroupPropertyFilter;
import cn.fastmc.core.pagination.PropertyFilter;
import cn.fastmc.core.utils.FunctionUtils;
import cn.fastmc.core.utils.RequestUtils;
import cn.fastmc.core.utils.ResponseUtils;
import cn.fastmc.sqlconfig.query.BaseQuery;
import cn.fastmc.viewconfig.components.Form;



public abstract class BaseController<T extends PersistableEntity<ID>, ID extends Serializable> implements ServletContextAware {
	

	
	protected static final String PAGINH_SQL = "pagingSql";
	protected static final String PAGING_COUNTSQL  = "pagingCountSql";
	protected static final String LIST_SQL = "listSql";
	
	protected ServletContext servletContext;
	@Autowired
	protected MessageSource messageSource;
	
	@Resource(name="jdbcQuery")
	protected BaseQuery nativeQuery;
	
	/** 泛型对应的Class定义 */
    protected Class<T> entityClass;

    /** 泛型对应的Class定义 */
    protected Class<ID> entityIdClass;
   
    /** 表单配置参数  **/
    protected String pageId;
	/** 表单配置参数 表单标识 **/
    protected String formId;
    /** 自定义表单路径**/
    protected String templatePath;
    
	/** 子类指定泛型对应的实体Service接口对象 */
    abstract protected BaseService<T, ID> getEntityService();
    
    private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    private static final Log logger = LogFactory.getLog(BaseController.class);

	    /**
	     * 初始化构造方法，计算相关泛型对象
	     */
	    @SuppressWarnings("unchecked")
	    public BaseController() {
	        super();
	        // 通过反射取得Entity的Class.
	        try {
	            Object genericClz = getClass().getGenericSuperclass();
	            if (genericClz instanceof ParameterizedType) {
	                entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	                entityIdClass = (Class<ID>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	            }
	        } catch (Exception e) {
	            throw new ServiceException(e.getMessage(), e);
	        }
	    }
	    
	   @Override
		public void setServletContext(ServletContext servletContext){
			    this.servletContext = servletContext;
		}
	    // --------------------------------------  
	    // -----------分页处理相关逻辑------------
	    // --------------------------------------
	   
		protected  Pageable buildPageableFromHttpRequest(
				HttpServletRequest request) {
			String rows = StringUtils.isBlank(request.getParameter("rows")) ? "10": request.getParameter("rows");
			if (Integer.valueOf(rows) < 0) {
				return null;
			}
			String page = StringUtils.isBlank(request.getParameter("page")) ? "1": request.getParameter("page");
			Sort sort = buildSortFromHttpRequest(request);
			return new PageRequest(Integer.valueOf(page) - 1,
					Integer.valueOf(rows), sort);
		}
		
	
		protected  Sort buildSortFromHttpRequest(HttpServletRequest request) {
			 String orderProperty = request.getParameter("orderProperty");
			 Direction sord = "desc".equalsIgnoreCase(request.getParameter("orderDirection")) ? Direction.DESC : Direction.ASC;
			 Sort sort = null;
	         if (StringUtils.isNotBlank(orderProperty)) {
	             sort = new Sort(sord, orderProperty);
	         }
	        return sort;
	    }
		/****
		 * 用于兼容SQL配置文件 方法的分页查询
		 */
		@SuppressWarnings("unchecked")
		protected Page<?> findByPage(Pageable pageable,Map<String,String> queryparam,BaseQuery baseQuery){
				Assert.notNull(queryparam.get(PAGINH_SQL), "pagingSql name must not be null");
				Assert.notNull(queryparam.get(PAGING_COUNTSQL), "pagingCountSql name must not be null");
				List<?> pageData = (List<?>)baseQuery.findPages(queryparam.get(PAGINH_SQL), queryparam, (pageable.getPageNumber()*pageable.getPageSize()),pageable.getPageSize());
				long pages = baseQuery.findCount(queryparam.get(PAGING_COUNTSQL), queryparam);
				return new PageImpl(pageData,pageable,pages);
	   }
		/**
		 * 本地SQL查询分页
		 * @param queryparam 查询参数
		 * @param request
		 * @param response
		 */
		@RequestMapping("findByNativePage")
		public void  findByNativePage(@RequestParam Map<String,String> queryparam,HttpServletRequest request,HttpServletResponse response){
			Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(request);
			Page<?> page = findByPage( pageable,queryparam, getNativeQuery());;
			String strjson = null;
	    	try {
				strjson = FunctionUtils.encodeData2Json(page.getContent(), page.getTotalElements());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info(e);
			} 
			if (logger.isDebugEnabled()) {
				logger.info(strjson);
			}
			ResponseUtils.renderJson(response,strjson); 
		}
		
		/**
	     * 分页列表显示数据
	     */
		@RequestMapping("findByPage")
	    public  void findByJpaPage(HttpServletRequest request,HttpServletResponse response) {
	        //
	    	Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(request);

	    	GroupPropertyFilter groupFilters = GroupPropertyFilter.buildFromHttpRequest(entityClass, request);
	        Page<T> page = this.getEntityService().findByPage( pageable,groupFilters);
	        String strjson = null;
	    	try {
				strjson = FunctionUtils.encodeData2Json(page.getContent(), page.getTotalElements());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info(e);
			} 
			if (logger.isDebugEnabled()) {
				logger.info(strjson);
			}
			ResponseUtils.renderJson(response,strjson); 
	    }
	    /**
	     * 子类额外追加过滤限制条件的入口方法，一般基于当前登录用户强制追加过滤条件
	     * 注意：凡是基于当前登录用户进行的控制参数，一定不要通过页面请求参数方式传递，存在用户篡改请求数据访问非法数据的风险
	     * 因此一定要在Controller层面通过覆写此回调函数或自己的业务方法中强制追加过滤条件
	     * @param filters 已基于Request组装好查询条件的集合对象
	     */
	    protected void appendFilterProperty(GroupPropertyFilter groupPropertyFilter) {

	    }
	    /**
	     * 根据当前登录用户对数据对象进行访问控制权限检查
	     * @param entity 待update可操作性检查对象
	     * @exception 如果检查没有权限，则直接抛出运行异常即可
	     */
	    protected void checkEntityAclPermission(T entity) {
	        //Do nothing check
	    }
	    protected T newBindingEntity() {
	        try {
	            return entityClass.newInstance();
	        } catch (InstantiationException e) {
	            logger.error(e.getMessage(), e);
	        } catch (IllegalAccessException e) {
	            logger.error(e.getMessage(), e);
	        }
	        return null;
	    }
	    
	    protected T newBindingEntity(HttpServletRequest request) {
	        try {
	        	T bindingEntity = entityClass.newInstance();
	        	Map<String,String> param = RequestUtils.getQueryParams(request);
		        FunctionUtils.populateAndNull(bindingEntity, param);
	            return bindingEntity;
	        } catch (InstantiationException e) {
	            logger.error(e.getMessage(), e);
	        } catch (IllegalAccessException e) {
	            logger.error(e.getMessage(), e);
	        }
	        return null;
	    }
	    
	    /**
	     * 将指定参数转换为ID泛型对应的主键变量实例
	     * 另外，页面也会以Struts标签获取显示当前操作对象的ID值
	     * @return ID泛型对象实例
	     */
	    @SuppressWarnings("unchecked")
	    public ID getId(String entityId) {
	        if (StringUtils.isBlank(entityId) || entityId.startsWith("-")) {
	            return null;
	        }
	        if (String.class.isAssignableFrom(entityIdClass)) {
	            return (ID) entityId;
	        } else if (Long.class.isAssignableFrom(entityIdClass)) {
	            return (ID) (Long.valueOf(entityId));
	        } else {
	            throw new IllegalStateException("Undefine entity ID class: " + entityIdClass);
	        }
	    }
	    @MetaData(value = "任意调用service类方法")
	    @RequestMapping("invokeMethod/{method}")
	    public void invokeMethod( String method,HttpServletRequest request,HttpServletResponse response) throws ServiceException {
			try {
				
				Map<String,String> param = RequestUtils.getQueryParams(request);
				final Object obj = getEntityService();   
					Method[] cmethod = null;
					if ("java.lang.Object".equals(obj.getClass().getSuperclass().getName())) {
						cmethod = obj.getClass().getDeclaredMethods();
					} else {
						cmethod = obj.getClass().getMethods();
					}
					Method methodInstance = null;
					for (int i = 0; i < cmethod.length; i++) {
						if (method.equals(cmethod[i].getName())) {
							methodInstance = cmethod[i];
							break;
						}
					}
				Assert.notNull(methodInstance, "invokeMethod method  name  must not exit");	
				Class<?>[] ps = methodInstance.getParameterTypes();
				Object inParam[] = new Object[ps.length];
				String[] parameterNames = parameterNameDiscoverer.getParameterNames(methodInstance);
				for (int i = 0; i < ps.length; i++) {
					if (String.class.isAssignableFrom(ps[i])) {
						inParam[i] = param.get(parameterNames[i].toString());
					} else if (Integer.class.isAssignableFrom(ps[i]) ){
						inParam[i]= new Integer((String)param.get(parameterNames[i].toString()));
					}else if(int.class.isAssignableFrom(ps[i])){
						inParam[i]= NumberUtils.toInt((String)param.get(parameterNames[i].toString()));
					//}else if (UserSessionBean.class.isAssignableFrom(ps[i])) {
						//inParam[i] =  sessionProvider.getUserSession(request);
					}else if(Collection.class.isAssignableFrom(ps[i])){
						    Type[] prarmType = methodInstance.getGenericParameterTypes();
							Type collectionType = prarmType[i];
							if (collectionType instanceof ParameterizedType) { 
								String arrayVlaue = param.get(parameterNames[i].toString());
								inParam[i] = Arrays.asList(StringUtils.split(arrayVlaue, ","));
							}    
					}else if(ps[i].isArray()){
						String arrayVlaue = param.get(parameterNames[i].toString());
						inParam[i] = StringUtils.split(arrayVlaue, ",");
					} else if( ps[i].isAssignableFrom(entityClass)){
						  inParam[i] = newBindingEntity();
						  FunctionUtils.populateAndNull(inParam[i], param);
					}else {
						inParam[i] = ps[i].newInstance();
						FunctionUtils.populateAndNull(inParam[i], param);
					}
				}

				Object rs = methodInstance.invoke(obj, inParam);
				if (rs != null) {
					if( rs instanceof ResultMessage){
						ResponseUtils.renderAjaxMessageMessage(response,(ResultMessage)rs);
					}else{
						ResponseUtils.renderAjaxJsonSuccessMessage(response,"操作成功");
					}
				} else {
					   ResponseUtils.renderAjaxJsonSuccessMessage(response,"操作成功");
				}

			} catch (ServiceException ex) {
			
				ResponseUtils.renderAjaxJsonErrorMessage(response,ex.toString());
			}catch(InvocationTargetException ex){
				logger.error(ex);
				ResponseUtils.renderAjaxJsonErrorMessage(response,messageSource.getMessage("ajax.error", null,Locale.getDefault()));
				throw new ServiceException(ex);
			}catch(IllegalAccessException ex){
				logger.error(ex);
				ResponseUtils.renderAjaxJsonErrorMessage(response,messageSource.getMessage("ajax.error", null,Locale.getDefault()));
				throw new ServiceException(ex);
			}catch(InstantiationException ex){
				logger.error(ex);
				ResponseUtils.renderAjaxJsonErrorMessage(response,messageSource.getMessage("ajax.error", null,Locale.getDefault()));
				throw new ServiceException(ex);
			}
	    }
	    
	    // --------------------------------------  
	    // -----------"增删改"处理相关逻辑------------
	    // --------------------------------------
	    
	   
	    
		protected ModelAndView buildDefaultInputView(T bindingEntity) {
			 return ResponseUtils.buildDefaultInputView(bindingEntity,pageId,formId,null);
		}

		 /**
	     * 转向创建对象录入页面
	     * @return
	     */
		@RequestMapping("create")
		public ModelAndView create() {
			 
	    	 return ResponseUtils.buildDefaultInputView(null,pageId,formId,null);
	    }


	    /**
	     * 编辑对象显示页面，在通用的prepare接口方法中已经准备好相关的binding对象
	     * 如果对象不需要区分create和update权限控制，则可以用此宽泛的编辑显示逻辑
	     * @return
	     */
		@RequestMapping("edit")
	    public ModelAndView edit(String ID) {
			T bindingEntity = null;
	    	ID id = getId(ID);
	    	if(null != id){
	    		 bindingEntity = getEntityService().findOne(id);
	    	}
	        return buildDefaultInputView(bindingEntity);
	    }

	    /**
	     * 此方法除了可以用于前端页面以EL方式控制"创建","修改","删除"操作按钮的disabled状态
	     * @param entity 待create可操作性检查对象
	     * @return 返回不允许操作错误提示消息，如果为空则表示允许操作
	     */
	    public void isDisallowOperate(ModelMap model) {
	    	model.addAttribute("allowCreate", true);
	    	model.addAttribute("allowUpdate", true);
	    	model.addAttribute("allowDelete", true);
	    }
	    
	

	    /**
	     * 为了避免由于权限配置不严格，导致未授权的Controller数据操作访问，父类提供protected基础实现，子类根据需要覆写public然后调用基类方法
	     * @return JSON操作提示
	     */
	    @MetaData(value = "创建")
	    @RequestMapping("doCreate")
	    public void doCreate(HttpServletRequest request,HttpServletResponse response) {
	    	//检查提交的数据参数符合用户ACL权限，否则拒绝创建数据
	        //checkEntityAclPermission(bindingEntity);
	     	T bindingEntity = newBindingEntity(request);
	    	this.getEntityService().save(bindingEntity);
	    	ResponseUtils.renderAjaxJsonSuccessMessage(response,"操作成功");
	    }

	    /**
	     * 为了避免由于权限配置不严格，导致未授权的Controller数据操作访问，父类提供protected基础实现，子类根据需要覆写public然后调用基类方法
	     * @return JSON操作提示
	     */
	    @MetaData(value = "更新")
	    @RequestMapping("doUpdate")
	    public void doUpdate(HttpServletRequest request,HttpServletResponse response) {
	    	//检查提交的数据参数符合用户ACL权限，否则拒绝创建数据
	        //checkEntityAclPermission(bindingEntity);
	    	T bindingEntity = newBindingEntity(request);
	    	this.getEntityService().save(bindingEntity);
	    	ResponseUtils.renderAjaxJsonSuccessMessage(response,"操作成功");
	    }
	    @MetaData(value = "删除")
	    @RequestMapping("doDelete")
	    public void doDelete(String ID,HttpServletRequest request,HttpServletResponse response) {
	    	//检查提交的数据参数符合用户ACL权限，否则拒绝创建数据
	        //checkEntityAclPermission(bindingEntity);
	    	if(StringUtils.isNotEmpty(ID)){
	    		ID id = getId(ID);
	    		T entity = this.getEntityService().findOne(id);
	    		this.getEntityService().delete(entity);
	    	}
	    	ResponseUtils.renderAjaxJsonSuccessMessage(response,"操作成功");
	    }
	  
	    
	    protected String getPageId() {
			return pageId;
		}

	    protected void setPageId(String pageId) {
			this.pageId = pageId;
		}

		protected String getFormId() {
			return formId;
		}

		protected void setFormId(String formId) {
			this.formId = formId;
		}

		protected String getTemplatePath() {
			return templatePath;
		}

		protected void setTemplatePath(String templatePath) {
			this.templatePath = templatePath;
		}

		protected BaseQuery getNativeQuery() {
			return nativeQuery;
		}

		protected void setNativeQuery(BaseQuery nativeQuery) {
			this.nativeQuery = nativeQuery;
		}


}
