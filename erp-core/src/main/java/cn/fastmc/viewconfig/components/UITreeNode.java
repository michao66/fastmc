package cn.fastmc.viewconfig.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



public class UITreeNode {
	protected String text;
	protected String icon;
	protected String id;
	protected Boolean ischecked;
	protected Boolean allowChildren;
	protected Boolean disabled;
	protected Boolean expandable;
	protected Boolean expanded;
	protected String href;
	protected String parentId;
	protected String hrefTarget;
	protected Boolean leaf;
	protected String qtip;
	protected Boolean singleClickExpand;
	protected String cls;
	protected String iconCls;
    private String moduleType;
    //适用于EasyUI扩展
    private String state;
    private Map<String,String> attributes  ;
	private Map<String,String>  data;
	protected Boolean isexpand;
	

	protected Boolean delayExpand;
    //适用于EasyUI扩展

    public UITreeNode(){
    	attributes = new HashMap<String,String>();
    	data = new  HashMap<String,String>();
    }
    public void  addData(String key,String value){
    	data.put(key, value);
    }
    public void  addAttributes(String key,String value){
    	attributes.put(key, value);
    }
    public Map<String, String> getAttributes() {
  		return attributes;
  	}
  	public void setAttributes(Map<String, String> attributes) {
  		this.attributes = attributes;
  	}
  	public Map<String, String> getData() {
  		return data;
  	}
  	public void setData(Map<String, String> data) {
  		this.data = data;
  	}
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	protected Boolean cascade;

	
	private UITreeNode parent;
	
	private List<UITreeNode> children = new ArrayList<UITreeNode>();;
	
	public List<UITreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<UITreeNode> children) {
		this.children = children;
	}
	
	public void add(UITreeNode node) {
		node.setParent(this);
		if (this.children == null)
			this.children = new LinkedList<UITreeNode>();
		this.children.add(node);
	}
	
	
	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setExpandable(java.lang.Boolean value) {
		this.expandable = value;
	}

	public void setExpanded(java.lang.Boolean value) {
		this.expanded = value;
	}

	public java.lang.Boolean getExpandable() {
		if (this.expandable != null) {
			return this.expandable;
		}
		return null;
	}

	public java.lang.Boolean getExpanded() {
		if (this.expanded != null) {
			return this.expanded;
		}
		return null;
	}

	public java.lang.String getHref() {
		if (this.href != null) {
			return this.href;
		}

		return null;
	}

	public void setHref(java.lang.String value) {
		this.href = value;
	}

	public java.lang.String getHrefTarget() {
		if (this.hrefTarget != null) {
			return this.hrefTarget;
		}

		return null;
	}

	public void setHrefTarget(java.lang.String value) {
		this.hrefTarget = value;
	}

	public java.lang.Boolean getLeaf() {
		if (this.leaf != null) {
			return this.leaf;
		}
		return null;
	}

	public void setLeaf(java.lang.Boolean value) {
		this.leaf = value;
	}

	public java.lang.String getQtip() {
		if (this.qtip != null) {
			return this.qtip;
		}

		return null;
	}

	public void setQtip(java.lang.String value) {
		this.qtip = value;
	}

	public java.lang.String getCls() {
		if (this.cls != null) {
			return this.cls;
		}

		return null;
	}

	public void setCls(java.lang.String value) {
		this.cls = value;
	}

	public java.lang.String getIconCls() {
		if (this.iconCls != null) {
			return this.iconCls;
		}

		return null;
	}

	public void setIconCls(java.lang.String value) {
		this.iconCls = value;
	}

	public java.lang.Boolean getCascade() {
		if (this.cascade != null) {
			return this.cascade;
		}

		return null;
	}

	public void setCascade(java.lang.Boolean value) {
		this.cascade = value;
	}




	public UITreeNode getParent() {
		return parent;
	}


	public void setParent(UITreeNode parent) {
		this.parent = parent;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}
	
	public Boolean getIschecked() {
		if (this.ischecked != null) {
			return this.ischecked;
		}

		return null;
	}
	public void setIschecked(Boolean ischecked) {
		this.ischecked = ischecked;
	}
	
	public Boolean getIsexpand() {
		return isexpand;
	}
	public void setIsexpand(Boolean isexpand) {
		this.isexpand = isexpand;
	}
	public Boolean getDelayExpand() {
		return delayExpand;
	}
	public void setDelayExpand(Boolean delayExpand) {
		this.delayExpand = delayExpand;
	}

}
