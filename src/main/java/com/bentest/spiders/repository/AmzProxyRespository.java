package com.bentest.spiders.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bentest.spiders.entity.AmzProxy;
@Repository
public interface AmzProxyRespository extends JpaRepository<AmzProxy, Long>, JpaSpecificationExecutor<AmzProxy> {
	List<AmzProxy> findTop200ByStatus(int status);
}
