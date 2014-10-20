package com.slsoft.auth.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import cn.fastmc.code.jpa.dao.SpringJpaDao;

import com.slsoft.auth.entify.User;

@Repository
public interface UserDao extends SpringJpaDao<User, String> {

    @QueryHints({ @QueryHint(name = org.hibernate.ejb.QueryHints.HINT_CACHEABLE, value = "true") })
    List<User> findBySigninid(String signid);

    
    
    @QueryHints({ @QueryHint(name = org.hibernate.ejb.QueryHints.HINT_CACHEABLE, value = "true") })
    User findByUserUid(String userUid);

    @QueryHints({ @QueryHint(name = org.hibernate.ejb.QueryHints.HINT_CACHEABLE, value = "true") })
    @Query("select count(*) from User")
    Long findUserCount();


}
