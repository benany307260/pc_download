package com.montnets.pc_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.montnets.pc_service.entity.AmzProduct;
@Repository
public interface AmzProductRespository extends JpaRepository<AmzProduct, Long>, JpaSpecificationExecutor<AmzProduct> {
	
}
