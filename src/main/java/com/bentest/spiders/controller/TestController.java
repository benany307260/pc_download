package com.bentest.spiders.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.bentest.spiders.constant.CmdType;
import com.bentest.spiders.entity.AmzCmdtask;
import com.bentest.spiders.entity.AmzDepartment;
import com.bentest.spiders.repository.AmzCmdtaskRespository;
import com.bentest.spiders.repository.AmzDepartmentRespository;

@RestController
public class TestController {
	
	@Autowired
	private AmzDepartmentRespository depRespository;
	
	@Autowired
	AmzCmdtaskRespository cmdtaskRespository;
	
	@RequestMapping("/start")
	public Boolean test() {
		List<AmzDepartment> depList = depRespository.findByDepLevelAndDepStatus(1, 0);
		if(depList == null || depList.size() < 1) {
			return false;
		}
		
		List<AmzCmdtask> cmdList = new ArrayList<>();
		
		for(AmzDepartment dep : depList) {
			AmzCmdtask cmd = new AmzCmdtask();
			cmd.setCmdStatus(0);
			cmd.setCmdType(CmdType.CMD102);
			
			String cmdTextJson = JSON.toJSONString(dep);
			
			cmd.setCmdText(cmdTextJson);
			
			cmd.setCreateTime(new Date());
			cmd.setUpdateTime(new Date());
			
			cmdList.add(cmd);
		}
		
		if(cmdList.size() > 0) {
			cmdtaskRespository.saveAll(cmdList);
			return true;
		}
		
		return false;
	}
	
	@RequestMapping("/download")
	public Boolean download() {
		
		Optional<AmzDepartment> opt = depRespository.findById("4954955011");
		if(!opt.isPresent()) {
			return false;
		}
		AmzDepartment dep = opt.get();
		
		String cmdTextJson = JSON.toJSONString(dep);
		AmzCmdtask cmd102 = new AmzCmdtask(CmdType.CMD102, cmdTextJson);
		
		cmdtaskRespository.save(cmd102);
		return true;
	}
}
