package cn.fastmc.code.jpa.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.fastmc.core.jpa.repository.BasicJpaRepository;

public interface SpringJpaDao <T, ID extends Serializable> extends BasicJpaRepository<T, ID>,
JpaSpecificationExecutor<T> {

}
