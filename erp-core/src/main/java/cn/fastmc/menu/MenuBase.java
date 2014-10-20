/**
 * 系统模块菜单BEAN类
 * 考虑再三还决定用XML配置的方式来做，存在数据库也没什么不好
 * 但总觉那样不专业。
 */
package cn.fastmc.menu;


/**
 * 本模块只提供三级菜单管理，简单的菜单关系，对系统的易用性有很好的帮助。
 * <menus>
 *  <menu id="systeseting"  name="系统设置">
 *       <Item id="users_manage" name="用户管理" icon="" url="" isShow="true"/>
 *       <Item id="grant_manage" name="权限管理">
 *            <Item id="grant_manage" name="角色管理"/>
 *       </Item>
 *  </menu>
 * </menus>
 * @author michao
 * 菜单信息实体类
 */
public class MenuBase {
	
	protected String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	protected String title;
	
	protected String icon;
	
	protected String url;
	
	protected boolean isShow;
	


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isShow() {
		return isShow;
	}
	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}


}
