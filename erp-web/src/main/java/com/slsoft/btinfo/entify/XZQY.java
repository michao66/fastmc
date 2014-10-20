package com.slsoft.btinfo.entify;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.fastmc.core.annotation.MetaData;
import cn.fastmc.core.jpa.entity.PersistableEntity;
@Entity
@Table(name = "CMS_BT_XZQY")
@MetaData(value = "补贴-行政区域划分")
public class XZQY  extends PersistableEntity<String>{
	private String id;

	private String name;
	
	private Integer orgType;
	
	private String pinyin;	
	

	@Id
	@Column(length = 32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name="ORG_TYPE" ,nullable = false)
	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}
	
	@Column(length = 60)
	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
}
