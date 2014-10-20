package com.slsoft.auth.entify;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.fastmc.core.annotation.MetaData;

@Entity
@Table(name = "persistent_logins")
@MetaData(value = "登录持久化")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PersistentLogin {

    private String username;
    private String series;
    private String token;
    private Date lastUsed;

    @Id
    @Column(length = 64)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    @Column(length = 64)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(length = 64)
    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }



    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }
}
