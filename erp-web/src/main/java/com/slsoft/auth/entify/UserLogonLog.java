package com.slsoft.auth.entify;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.fastmc.common.web.DateTimeJsonSerializer;
import cn.fastmc.core.annotation.MetaData;
import cn.fastmc.core.jpa.entity.BaseUuidEntity;
import cn.fastmc.core.utils.DateUtils;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "AUTH_LOGON_LOG")
@Cache(usage = CacheConcurrencyStrategy.NONE)
@MetaData(value = "用户登录登出历史记录")
public class UserLogonLog extends BaseUuidEntity {

    private Boolean authenticationFailure = false;

    @MetaData(value = "登录账号")
    private String username;

    @MetaData(value = "账户编号")
    private String userid;

    @MetaData(value = "登录时间")
    private Date logonTime;

    @MetaData(value = "登出时间")
    private Date logoutTime;

    @MetaData(value = "登录时长")
    private Long logonTimeLength;

    @MetaData(value = "登录次数")
    private Integer logonTimes;

    @MetaData(value = "userAgent")
    private String userAgent;

    @MetaData(value = "xforwardFor")
    private String xforwardFor;

    @MetaData(value = "localAddr")
    private String localAddr;

    @MetaData(value = "localName")
    private String localName;

    @MetaData(value = "localPort")
    private Integer localPort;

    @MetaData(value = "remoteAddr")
    private String remoteAddr;

    @MetaData(value = "remoteHost")
    private String remoteHost;

    @MetaData(value = "remotePort")
    private Integer remotePort;

    @MetaData(value = "serverIP")
    private String serverIP;

    @MetaData(value = "Session编号")
    private String httpSessionId;

    @Column(length = 128, nullable = false, unique = true)
    public String getHttpSessionId() {
        return httpSessionId;
    }

    public void setHttpSessionId(String httpSessionId) {
        this.httpSessionId = httpSessionId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    public Date getLogonTime() {
        return logonTime;
    }

    public void setLogonTime(Date logonTime) {
        this.logonTime = logonTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    @Column(length = 1024, nullable = true)
    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Long getLogonTimeLength() {
        return logonTimeLength;
    }

    public void setLogonTimeLength(Long logonTimeLength) {
        this.logonTimeLength = logonTimeLength;
    }

    @Column(length = 1024, nullable = true)
    public String getXforwardFor() {
        return xforwardFor;
    }

    public void setXforwardFor(String xforwardFor) {
        this.xforwardFor = xforwardFor;
    }

    @Column(length = 128, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(length = 128, nullable = false)
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(length = 128, nullable = true)
    public String getLocalAddr() {
        return localAddr;
    }

    public void setLocalAddr(String localAddr) {
        this.localAddr = localAddr;
    }

    @Column(length = 128, nullable = true)
    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public Integer getLocalPort() {
        return localPort;
    }

    public void setLocalPort(Integer localPort) {
        this.localPort = localPort;
    }

    @Column(length = 128, nullable = true)
    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    @Column(length = 128, nullable = true)
    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public Integer getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(Integer remotePort) {
        this.remotePort = remotePort;
    }

    @Column(length = 128, nullable = true)
    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public Integer getLogonTimes() {
        return logonTimes;
    }

    public void setLogonTimes(Integer logonTimes) {
        this.logonTimes = logonTimes;
    }



    @Transient
    public String getLogonTimeLengthFriendly() {
        return DateUtils.getHumanDisplayForTimediff(logonTimeLength);
    }

    public Boolean getAuthenticationFailure() {
        return authenticationFailure;
    }

    public void setAuthenticationFailure(Boolean authenticationFailure) {
        this.authenticationFailure = authenticationFailure;
    }
}