package com.slsoft.auth.entify;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.fastmc.core.annotation.MetaData;
import cn.fastmc.core.jpa.entity.BaseUuidEntity;

@Entity
@Table(name = "AUTH_USER_OAUTH")
@MetaData(value = "用户绑定OAUTH认证")
public class UserOauth extends BaseUuidEntity {

    private User user;
    private String username;
    private String providerUid;
    private String providerType;
    private Date bindTime;

    @Column(length = 128, nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(length = 128, nullable = false)
    public String getProviderUid() {
        return providerUid;
    }

    public void setProviderUid(String providerUid) {
        this.providerUid = providerUid;
    }

    @Column(length = 128, nullable = false)
    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getBindTime() {
        return bindTime;
    }

    public void setBindTime(Date bindTime) {
        this.bindTime = bindTime;
    }


}
