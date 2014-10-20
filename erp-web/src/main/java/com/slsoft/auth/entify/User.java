package com.slsoft.auth.entify;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;

import cn.fastmc.common.web.DateJsonSerializer;
import cn.fastmc.common.web.DateTimeJsonSerializer;
import cn.fastmc.core.annotation.EntityAutoCode;
import cn.fastmc.core.annotation.MetaData;
import cn.fastmc.core.jpa.entity.BaseUuidEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;

@Entity
@Table(name = "AUTH_USER")
@MetaData(value = "用户")

public class User extends BaseUuidEntity {

    public final static String[] PROTECTED_USER_NAMES = new String[] { "admin", "super" };

 

    @MetaData(value = "登录账号")
    @EntityAutoCode(order = 10, search = true)
    private String signinid;

    @MetaData(value = "登录密码")
    @EntityAutoCode(order = 10, listShow = false)
    private String password;

    @MetaData(value = "昵称")
    @EntityAutoCode(order = 20, search = true)
    private String nick;

    @MetaData(value = "移动电话")
    private String mobilePhone;

    @MetaData(value = "电子邮件")
    private String email;

    @MetaData(value = "启用标识")
    private Boolean enabled = Boolean.TRUE;

    

    @MetaData(value = "账户未锁定标志")
    private Boolean accountNonLocked = Boolean.TRUE;

    @MetaData(value = "失效日期")
    private Date accountExpireTime;

    @MetaData(value = "账户密码过期时间")
    private Date credentialsExpireTime;

    @MetaData(value = "角色关联")
    private List<UserR2Role> userR2Roles = Lists.newArrayList();

    @MetaData(value = "用户唯一标识号")
    private String userUid;

    @MetaData(value = "最后登录时间")
    private Date lastLogonTime;

    @MetaData(value = "最后登录IP")
    private String lastLogonIP;

    @MetaData(value = "最后登录主机名")
    private String lastLogonHost;

    @MetaData(value = "总计登录次数")
    private Integer logonTimes;

    @MetaData(value = "总计认证失败次数")
    private Integer logonFailureTimes;

    @MetaData(value = "最后认证失败时间")
    private Date lastLogonFailureTime;

    @MetaData(value = "随机数")
    private String randomCode;

    /** 遗留项目属性定义 */
    @Deprecated
    private String userPin;

 

 

    @Size(min = 3, max = 30)
    @Column(length = 128, unique = true, nullable = false, updatable = false, name = "user_id")
    public String getSigninid() {
        return signinid;
    }

    public void setSigninid(String signinid) {
        this.signinid = signinid;
    }

    @Column(length = 128)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(length = 64)
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

   
    @Email
    @Column(length = 128)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = DateJsonSerializer.class)
    public Date getAccountExpireTime() {
        return accountExpireTime;
    }

    public void setAccountExpireTime(Date accountExpireTime) {
        this.accountExpireTime = accountExpireTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = DateJsonSerializer.class)
    public Date getCredentialsExpireTime() {
        return credentialsExpireTime;
    }

    public void setCredentialsExpireTime(Date credentialsExpireTime) {
        this.credentialsExpireTime = credentialsExpireTime;
    }

    @Type(type = "yes_no")
    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    //@NotAudited
    @JsonIgnore
    public List<UserR2Role> getUserR2Roles() {
        return userR2Roles;
    }

    public void setUserR2Roles(List<UserR2Role> userR2Roles) {
        this.userR2Roles = userR2Roles;
    }

    @Column(updatable = false, length = 64, unique = true, nullable = false)
    public String getUserUid() {
        return userUid;
    }

    
    public void setUserUid(String uid) {
        this.userUid = uid;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    public Date getLastLogonTime() {
        return lastLogonTime;
    }

    public void setLastLogonTime(Date lastLogonTime) {
        this.lastLogonTime = lastLogonTime;
    }

    @Column(length = 128, nullable = true)
    public String getLastLogonIP() {
        return lastLogonIP;
    }

    public void setLastLogonIP(String lastLogonIP) {
        this.lastLogonIP = lastLogonIP;
    }

    @Column(length = 128, nullable = true)
    public String getLastLogonHost() {
        return lastLogonHost;
    }

    public void setLastLogonHost(String lastLogonHost) {
        this.lastLogonHost = lastLogonHost;
    }

    public Integer getLogonTimes() {
        return logonTimes;
    }

    public void setLogonTimes(Integer logonTimes) {
        this.logonTimes = logonTimes;
    }

    @Type(type = "yes_no")
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

  
    @Column(length = 50)
    public String getUserPin() {
        return userPin;
    }

    public void setUserPin(String userPin) {
        this.userPin = userPin;
    }

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    public Integer getLogonFailureTimes() {
        return logonFailureTimes;
    }

    public void setLogonFailureTimes(Integer logonFailureTimes) {
        this.logonFailureTimes = logonFailureTimes;
    }

    public Date getLastLogonFailureTime() {
        return lastLogonFailureTime;
    }

    public void setLastLogonFailureTime(Date lastLogonFailureTime) {
        this.lastLogonFailureTime = lastLogonFailureTime;
    }

    @Column(length = 50)
    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
