package com.slsoft.auth.entify;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.fastmc.core.annotation.MetaData;
import cn.fastmc.core.jpa.entity.BaseUuidEntity;


@Entity
@Table(name = "AUTH_USER_R2_ROLE", uniqueConstraints = @UniqueConstraint(columnNames = { "USER_ID", "ROLE_ID" }))
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@MetaData(value = "角色与权限关联")
public class UserR2Role extends BaseUuidEntity {

    /** 关联用户对象 */
    private User user;

    /** 关联角色对象 */
    private Role role;
    
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", nullable = false)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    

}
