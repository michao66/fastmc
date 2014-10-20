package cn.fastmc.core.utils;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;
import org.springframework.web.servlet.LocaleResolver;

/**
 * 工具类 - Spring
 */
public class SpringUtils implements DisposableBean,  ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtils.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 根据Bean名称获取实例
	 * 
	 * @param name
	 *            Bean注册名称
	 * 
	 * @return bean实例
	 * 
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		Assert.hasText(name);
		return applicationContext.getBean(name);
	}
	
	 public static <T> T getBean(String name, Class<T> type)
	  {
	    Assert.hasText(name);
	    Assert.notNull(type);
	    return applicationContext.getBean(name, type);
	  }
	 /**
	     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	     */
	  public static <T> T getBean(Class<T> requiredType) {
	       Assert.notNull(applicationContext);
	       return applicationContext.getBean(requiredType);
	  }
	/**
	 * 获取信息根据code
	 * @param code 信息编号
	 * @param args 信息参数
	 * @return
	 */
	public static String getMessage(String code, Object[] args)
	  {
	    LocaleResolver localreslver = (LocaleResolver)getBean("localeResolver", LocaleResolver.class);
	    Locale localLocale = localreslver.resolveLocale(null);
	    return applicationContext.getMessage(code, args, localLocale);
	  }

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		this.applicationContext = null;
	}

}