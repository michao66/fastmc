package com.slsoft.core.security;

import cn.fastmc.core.utils.SpringUtils;

import com.slsoft.auth.dao.UserDao;
import com.slsoft.auth.entify.User;

/**
 * 基于Spring Security获取当前登录用户对象的帮助类
 *
 */
public class AuthUserHolder {

    /**
     * 获取当前登录用户对象
     */
    public static User getLogonUser() {
        UserDao userDao = SpringUtils.getBean(UserDao.class);
        return userDao.findByUserUid(AuthContextHolder.getAuthUserDetails().getUserUid());
    }

    /**
     * 获取当前登录用户所属部门对象
    
    public static Department getLogonUserDepartment() {
        User user = getLogonUser();
        if (user == null) {
            return null;
        }
        return user.getDepartment();
    } */
}
