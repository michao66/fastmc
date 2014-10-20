package cn.fastmc.sys.service;




import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import cn.fastmc.core.annotation.MetaData;

@Component
public class PropertiesConfigService {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesConfigService.class);

    @MetaData(value = "开发模式")
    private static boolean devMode = false;

    @MetaData(value = "演示模式")
    private static boolean demoMode = false;

    
    /**
     * @see ApplicationContextPreListener#contextInitialized
     */
    @MetaData(value = "Web应用部署的根目录")
    private static String webRootRealPath;
    
    
    private static String cookieDomain;
    
    private static String CookiePath;
  
	

	public static boolean isDemoMode() {
        return demoMode;
    }

    public static boolean isDevMode() {
        return devMode;
    }

    public static String getWebRootRealPath() {
        Assert.notNull(webRootRealPath, "WEB_ROOT real path undefined");
        return webRootRealPath;
    }

    @Value("${demo.mode:false}")
    public void setDemoMode(String demoMode) {
        PropertiesConfigService.demoMode = BooleanUtils.toBoolean(demoMode);
        logger.info("System runnging at demo.mode={}", PropertiesConfigService.demoMode);
    }

    @Value("${dev.mode:false}")
    public void setDevMode(String devMode) {
        PropertiesConfigService.devMode = BooleanUtils.toBoolean(devMode);
        logger.info("System runnging at dev.mode={}", PropertiesConfigService.devMode);
    }

    @Value("${web.root.real.path:''}")
    public static void setWebRootRealPath(String in) {
        if (webRootRealPath == null) {
            webRootRealPath = in;
        }
        if (StringUtils.isNotBlank(webRootRealPath)) {
            logger.info("System runnging at web.root.real.path={}", webRootRealPath);
        }
    }
    
   
    @Value("${web.cookie.path:'/'}")
  	public static void setCookiePath(String cookieDomain) {
  		PropertiesConfigService.cookieDomain = cookieDomain;
  	}
    
    public static String getCookiePath() {
		return CookiePath;
	}
    
    public static String getCookieDomain() {
  		return cookieDomain;
  	}
    @Value("${web.cookie.domain:''}")
	public static void setCookieDomain(String cookiePath) {
		CookiePath = cookiePath;
	}

}
