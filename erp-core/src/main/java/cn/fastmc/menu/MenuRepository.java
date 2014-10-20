package cn.fastmc.menu;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

import cn.fastmc.core.ServiceException;

public class MenuRepository {
	  private static Log log = LogFactory.getLog(MenuRepository.class);

	  public static final String MENU_REPOSITORY_KEY = "com.mich.erp.menu.MENU_REPOSITORY";
	  protected LinkedMap menus = new LinkedMap();
	  
	  
	  public void load(Resource xmlResource) throws ServiceException  {
	        InputStream input = null;
	        Digester digester = initDigester();
	        try {
	        	input = xmlResource.getInputStream();
	            digester.parse(input);
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new ServiceException("Error parsing resource file: " + xmlResource.getFilename() + " nested exception is: " + e.getMessage());
	        } finally {
	            try {
	                input.close();
	            } catch (Exception e) {
	            }
	        }
	    }

	

	  protected Digester initDigester() {
	        Digester digester = new Digester();
	        digester.setClassLoader(Thread.currentThread().getContextClassLoader());
	        digester.push(this);
	        digester.addObjectCreate("Menus/Menu", "com.mich.erp.menu.MenuComponent", "className");
	        digester.addSetProperties("Menus/Menu");
	        digester.addSetNext("Menus/Menu", "addMenu");
	        
	        digester.addObjectCreate("Menus/Menu/Item", "com.mich.erp.menu.MenuComponent", "className");
	        digester.addSetProperties("Menus/Menu/Item");
	        digester.addSetNext("Menus/Menu/Item", "addMenuComponent", "com.mich.erp.menu.MenuComponent");
	        
	        digester.addObjectCreate("Menus/Menu/Item/Item", "com.mich.erp.menu.MenuComponent", "className");
	        digester.addSetProperties("Menus/Menu/Item/Item");
	        digester.addSetNext("Menus/Menu/Item/Item", "addMenuComponent", "com.mich.erp.menu.MenuComponent");
	        return digester;
	  }
	  
	  public void addMenu(MenuComponent menu) {
	        if (menus.containsKey(menu.getId())) {            
	            if (log.isDebugEnabled()) {
	                log.warn("Menu '" + menu.getId() + "' already exists in repository");
	            }
	            List<MenuComponent> children = (getMenu(menu.getId())).getComponents();
	            if (children != null && menu.getComponents() != null) {
	                for (MenuComponent child : children) {
	                    menu.addMenuComponent(child);
	                }
	            }
	        }
	        menus.put(menu.getId(), menu);
	    }
	  
	  public MenuComponent getMenu(String menuName) {
	        return (MenuComponent) menus.get(menuName);
	  }
	  
	  public List<MenuComponent> getTopMenus() {
	        List<MenuComponent> topMenus = new ArrayList<MenuComponent>();
	        if (menus == null) {
	            log.warn("No menus found in repository!");
	            return topMenus;
	        }

	        for (Iterator it = menus.keySet().iterator(); it.hasNext();) {
	            String name = (String) it.next();
	            MenuComponent menu = getMenu(name);
	            if (menu.getParent() == null) {
	                topMenus.add(menu);
	            }
	        }
	        return topMenus;
	    }
}
