package com.bentest.spiders.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bentest.spiders.entity.AmzProxyIsp;
@Repository
public interface AmzProxyIspRespository extends JpaRepository<AmzProxyIsp, Integer>, JpaSpecificationExecutor<AmzProxyIsp> {
	List<AmzProxyIsp> findByProxyType(Integer proxyType);
}
