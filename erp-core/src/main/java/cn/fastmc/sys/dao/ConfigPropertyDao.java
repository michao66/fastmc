package cn.fastmc.sys.dao;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import cn.fastmc.code.jpa.dao.SpringJpaDao;
import cn.fastmc.sys.entify.ConfigProperty;

@Repository
public interface ConfigPropertyDao extends SpringJpaDao<ConfigProperty, String> {

    @QueryHints({ @QueryHint(name = org.hibernate.ejb.QueryHints.HINT_CACHEABLE, value = "true") })
    ConfigProperty findByPropKey(String propKey);
}