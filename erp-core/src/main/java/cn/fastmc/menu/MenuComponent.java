package cn.fastmc.menu;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;


public class MenuComponent extends MenuBase {
	
    protected static MenuComponent[] _menuComponent = new MenuComponent[0];

    protected List<MenuComponent> menuComponents = new ArrayList<MenuComponent>();
    protected MenuComponent parentMenu;
    
    public void addMenuComponent(MenuComponent menuComponent) {
        if (!menuComponents.contains(menuComponent)) {
            menuComponents.add(menuComponent);
            menuComponent.setParent(this);
        }
    }
    public MenuComponent[] getMenuComponents() {
        return (MenuComponent[]) menuComponents.toArray(_menuComponent);
    }
    
    public void setParent(MenuComponent parentMenu) {
        if (parentMenu != null) {
            if (!parentMenu.getComponents().contains(this)) {
                parentMenu.addMenuComponent(this);
            }
        }
        this.parentMenu = parentMenu;
    }

    public MenuComponent getParent() {
        return parentMenu;
    }
    
    public int getMenuDepth() {
        return getMenuDepth(this, 0);
    }

    private int getMenuDepth(MenuComponent menu, int currentDepth) {
        int depth = currentDepth + 1;

        MenuComponent[] subMenus = menu.getMenuComponents();
        if (subMenus != null) {
            for (int a = 0; a < subMenus.length; a++) {
                int depthx = getMenuDepth(subMenus[a], currentDepth + 1);
                if (depth < depthx)
                    depth = depthx;
            }
        }

        return depth;
    }
    
    public List<MenuComponent> getComponents() {
        return menuComponents;
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof MenuComponent)) {
            return false;
        }
        MenuComponent m = (MenuComponent) o;
        // Compare using StringUtils to avoid NullPointerExceptions
        return StringUtils.equals(m.getId(), this.id) &&
                StringUtils.equals(m.getIcon(), this.icon) &&
                StringUtils.equals(m.getTitle(), this.title) &&
                StringUtils.equals(m.getUrl(), this.url);
    }

}
