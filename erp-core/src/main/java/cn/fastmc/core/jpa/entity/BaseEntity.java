package cn.fastmc.core.jpa.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.groups.Default;

import cn.fastmc.core.jpa.listener.EntityListener;

@EntityListeners({EntityListener.class})
@MappedSuperclass
public abstract class BaseEntity <ID extends Serializable> extends PersistableEntity<ID> {
   

	public abstract  void setId(ID id);
	

	public static final String ID_PROPERTY_NAME = "id";
	public static final String CREATE_DATE_PROPERTY_NAME = "createDate";
	public static final String MODIFY_DATE_PROPERTY_NAME = "modifyDate";
	private Date createDate;//创建时间
	private Date modifyDate;//修改时间

	@Column(nullable = false, updatable = false)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(nullable = false)
	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public interface Save extends Default{
		
	}
	public interface Update extends Default{
		
	}

	


}
