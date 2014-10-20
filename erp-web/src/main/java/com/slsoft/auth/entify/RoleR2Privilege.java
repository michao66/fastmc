package com.slsoft.auth.entify;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.fastmc.core.annotation.MetaData;
import cn.fastmc.core.jpa.entity.BaseUuidEntity;

@Entity
@Table(name = "AUTH_ROLE_R2_PRIVILEGE", uniqueConstraints = @UniqueConstraint(columnNames = { "PRIVILEGE_ID",
        "ROLE_ID" }))
@MetaData(value = "角色与权限关联")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleR2Privilege extends BaseUuidEntity {

    /** 关联权限对象 */
    private Privilege privilege;

    /** 关联角色对象 */
    private Role role;
    
    @ManyToOne
    @JoinColumn(name = "PRIVILEGE_ID", nullable = false)
    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", nullable = false)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Transient

    public String getDisplay() {
        return privilege.getDisplay() + "_" + role.getDisplay();
    }
}
