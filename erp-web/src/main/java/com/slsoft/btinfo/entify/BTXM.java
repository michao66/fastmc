package com.slsoft.btinfo.entify;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.fastmc.core.annotation.MetaData;
import cn.fastmc.core.jpa.entity.PersistableEntity;

@Entity
@Table(name = "CMS_BT_BTXM")
@MetaData(value = "补贴项目")
public class BTXM extends PersistableEntity<String>{

	private String id;

	private String name;
	
	private String parent;
	@Id
	@Column(length = 32)
	@Override
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(nullable = false, length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(length = 32, nullable = true)
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

}
