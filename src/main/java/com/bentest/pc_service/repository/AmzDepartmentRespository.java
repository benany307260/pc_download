package com.bentest.pc_service.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.bentest.pc_service.entity.AmzDepartment;
@Repository
public interface AmzDepartmentRespository extends JpaRepository<AmzDepartment, String>, JpaSpecificationExecutor<AmzDepartment> {
	
	/**
	 * 获取类目
	 * @param depLevel
	 * @param depStatus
	 * @return
	 */
	List<AmzDepartment> findByDepLevelAndDepStatus(Integer depLevel, Integer depStatus);
}
