package com.slsoft.btinfo.entify;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import cn.fastmc.core.annotation.MetaData;
import cn.fastmc.core.jpa.entity.PersistableEntity;
@Entity
@Table(name = "cms_bt_field")
@MetaData(value = "补贴字段")
public class BTField  extends PersistableEntity<String>{
	private String id;
   
	private String fildId;
	

	private BTXM btxm;

	

	private String lable;
	
	private String type;
	
	private String attribute;  
	private String project;
	
	private Integer orderNum;
	
	

	@Id
	@Column(length = 32)
	@GenericGenerator(name="idGenerator", strategy="uuid") 
	@GeneratedValue(generator="idGenerator") 
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(nullable = false,length = 50 )
	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}
	@Column(nullable = false,length = 45 )
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(nullable = false,length = 100 )
	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	@Column(nullable = false,length = 50 )
	public String getFildId() {
		return fildId;
	}

	public void setFildId(String fildId) {
		this.fildId = fildId;
	}

	@Transient
	public String getProjectText(){
		if(null != btxm){
			return btxm.getName();
		}
		return "";
	}
	@Column(nullable = false,length = 50 , insertable=false, updatable=false)
	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	
	@ManyToOne
    @JoinColumn(name = "project")
	public BTXM getBtxm() {
		return btxm;
	}

	public void setBtxm(BTXM btxm) {
		this.btxm = btxm;
	}
	@Column
	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
}
