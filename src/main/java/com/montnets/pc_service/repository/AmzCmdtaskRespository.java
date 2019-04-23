package com.montnets.pc_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.montnets.pc_service.entity.AmzCmdtask;
@Repository
public interface AmzCmdtaskRespository extends JpaRepository<AmzCmdtask, Integer>, JpaSpecificationExecutor<AmzCmdtask> {
	
}
