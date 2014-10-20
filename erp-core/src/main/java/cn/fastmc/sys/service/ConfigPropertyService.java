package cn.fastmc.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.fastmc.code.jpa.dao.SpringJpaDao;
import cn.fastmc.core.jpa.service.BaseService;
import cn.fastmc.sys.dao.ConfigPropertyDao;
import cn.fastmc.sys.entify.ConfigProperty;

@Service
@Transactional
public class ConfigPropertyService extends BaseService<ConfigProperty,String>{
    
    @Autowired
    private ConfigPropertyDao configPropertyDao;

    @Override
    protected SpringJpaDao<ConfigProperty, String> getEntityDao() {
        return configPropertyDao;
    }
    
    public ConfigProperty findByPropKey(String propKey){
        return configPropertyDao.findByPropKey(propKey);
    }
}
