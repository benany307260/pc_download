package com.bentest.spiders.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.bentest.spiders.constant.CmdType;
import com.bentest.spiders.entity.AmzCmdtask;
import com.bentest.spiders.entity.AmzDepartment;
import com.bentest.spiders.httppool.HttpConnection;
import com.bentest.spiders.httppool.HttpConnectionFactory;
import com.bentest.spiders.httppool.HttpConnectionPool;
import com.bentest.spiders.httppool.HttpPoolManager;
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
		
		//Optional<AmzDepartment> opt = depRespository.findById("4954955011");
		Optional<AmzDepartment> opt = depRespository.findById("2562090011");
		if(!opt.isPresent()) {
			return false;
		}
		AmzDepartment dep = opt.get();
		
		String cmdTextJson = JSON.toJSONString(dep);
		AmzCmdtask cmd102 = new AmzCmdtask(CmdType.CMD102, cmdTextJson);
		
		cmdtaskRespository.save(cmd102);
		return true;
	}
	
	@RequestMapping("/conntest")
	public String request() {
		try {
			
			HttpConnection conn = HttpPoolManager.getInstance().getConnection();
			String url = "https://nghttp2.org/httpbin/get";
			String resp = conn.sendGetUseH2(url);
			HttpPoolManager.getInstance().returnConnection(conn);
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
