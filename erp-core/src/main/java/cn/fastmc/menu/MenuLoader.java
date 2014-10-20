package cn.fastmc.menu;



import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextException;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.WebApplicationObjectSupport;

public class MenuLoader extends WebApplicationObjectSupport {
	
	  private static Log log = LogFactory.getLog(MenuLoader.class);

	  private String menuConfig = "/WEB-INF/menu-config.xml";
	 
	  private  Resource menuXmlLocations;
	  
	  public Resource getMenuXmlLocations() {
		return menuXmlLocations;
	}

	public void setMenuXmlLocations(Resource menuXmlLocations) {
		this.menuXmlLocations = menuXmlLocations;
	}

	protected void initApplicationContext() throws ApplicationContextException {
	        try {
	            if (log.isDebugEnabled()) {
	                log.debug("Starting struts-menu initialization");
	            }
	            MenuRepository repository = new MenuRepository();
	            try {
	                repository.load(menuXmlLocations);
	                getServletContext().setAttribute(MenuRepository.MENU_REPOSITORY_KEY, repository);

	                if (log.isDebugEnabled()) {
	                    log.debug("struts-menu initialization successful");
	                }
	            } catch (Exception lre) {
	                throw new ServletException("Failure initializing struts-menu: " + lre.getMessage());
	            }
	        } catch (Exception ex) {
	            throw new ApplicationContextException("Failed to initialize Struts Menu repository", ex);
	        }
	    }
}
