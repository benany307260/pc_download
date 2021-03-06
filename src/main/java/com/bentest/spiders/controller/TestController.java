package com.bentest.spiders.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.bentest.spiders.aliyunoss.OSSService;
import com.bentest.spiders.config.SystemConfig;
import com.bentest.spiders.constant.CmdType;
import com.bentest.spiders.entity.AmzCmdtask;
import com.bentest.spiders.entity.AmzDepartment;
import com.bentest.spiders.httppool.HttpConnection;
import com.bentest.spiders.httppool.HttpPoolManager;
import com.bentest.spiders.repository.AmzCmdtaskRespository;
import com.bentest.spiders.repository.AmzDepartmentRespository;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.StrUtil;

@RestController
public class TestController {
	
	@Autowired
	private AmzDepartmentRespository depRespository;
	
	@Autowired
	AmzCmdtaskRespository cmdtaskRespository;
	
	@Autowired
    private SystemConfig systemConfig;
	
	@Autowired
	private OSSService ossService;
	
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
		Optional<AmzDepartment> opt = depRespository.findById("16225011011");
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
		HttpConnection conn = HttpPoolManager.getInstance().getConnection();
		try {
			
			String url = "https://www.amazon.com/s/browse?_encoding=UTF8&node=4954955011&ref_=nav_shopall-export_nav_mw_sbd_intl_arts";
			//String url = "https://www.ustc.edu.cn/";
			//String url = "https://www.yale.edu/";
			//String url = "https://nghttp2.org/httpbin/get";
			//String url = "https://nghttp2.org/httpbin/cookies/set/b/6";
			//String url = "https://www.amazon.com";
			//String url = "https://www.jd.com";
			
			String resp = conn.send(url);
			if(StrUtil.isBlank(resp)) {
				return "false";
			}
			
			FileWriter fileWriter = new FileWriter("D:\\temp\\temp.html");
			fileWriter.write(resp);
			
			//System.out.println(resp);
			return "true";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		finally {
			HttpPoolManager.getInstance().returnConnection(conn);
		}
	}
	
	@RequestMapping("/ossup")
	public Boolean ossup() {
		return ossService.uploadString("text20190708.txt", "testddddddddd23423423zxcvbnm,");
	}
	
	@RequestMapping("/ossdown")
	public String ossdown() {
		return ossService.downloadString("text20190708.txt");
	}
}
