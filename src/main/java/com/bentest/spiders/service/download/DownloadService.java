package com.bentest.spiders.service.download;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bentest.spiders.constant.AMZConstant;
import com.bentest.spiders.constant.CmdType;
import com.bentest.spiders.entity.AmzCmdtask;
import com.bentest.spiders.entity.AmzDepartment;
import com.bentest.spiders.repository.AmzCmdtaskRespository;

import cn.hutool.core.util.StrUtil;

@Service
public class DownloadService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/*@Autowired
	private AmzDepartmentRespository amzDepartmentRespository;*/
	
	@Autowired
	AmzCmdtaskRespository cmdtaskRespository;
	
	/*@Autowired
    private SystemConfig systemConfig;*/
	
	public int dealSonDepDownload(String cmdText) {
		
		try {
			if(StrUtil.isBlank(cmdText)) {
				log.error("处理子类目下载，指令内容为空。");
				return -1;
			}
			
			AmzDepartment parentDep = JSON.parseObject(cmdText, AmzDepartment.class);
			if(parentDep == null) {
				log.error("处理子类目下载，类目对象为null。cmdText="+cmdText);
				return -2;
			}
			
			// TODO 执行下载
			
			// TODO 下载成功，返回html文件路径
			String htmlFilePath = "C:\\Users\\lenovo\\git\\pc_service\\page\\list-page\\Arts & Crafts-123456789.html";
			//String htmlFilePath = "C:\\Users\\lenovo\\git\\pc_service\\page\\list-page\\Painting, Drawing & Art Supplies-123456789.html";
			//String htmlFilePath = "C:\\Users\\lenovo\\git\\pc_service\\page\\list-page\\Art Paper-123456789.html";
			//String htmlFilePath = "C:\\Users\\lenovo\\git\\pc_service\\page\\list-page\\Artist Trading Cards-123456789.html";
			
			
			//String htmlFilePath = "F:\\study\\amz\\git\\pc_service\\page\\list-page\\Arts & Crafts-123456789.html";
			
			parentDep.setHtmlFilePath(htmlFilePath);
			
			List<AmzCmdtask> cmdList = new ArrayList<>();
			
			// 通知处理子类目html
			String cmdTextJson = JSON.toJSONString(parentDep);
			AmzCmdtask cmd103 = new AmzCmdtask(CmdType.CMD103, cmdTextJson);
			cmdList.add(cmd103);
			//cmdtaskRespository.save(cmd103);
			
			// 通知处理产品list
			Map<String,String> dataMap = new HashMap<>();
			dataMap.put(AMZConstant.CMD_KEY_PAGE_TYPE, String.valueOf(AMZConstant.VALUE_PAGE_TYPE_FIRST));
			dataMap.put(AMZConstant.CMD_KEY_HTML_FILE_PATH, htmlFilePath);
			cmdTextJson = JSON.toJSONString(dataMap);
			AmzCmdtask cmd107 = new AmzCmdtask(CmdType.CMD107, cmdTextJson);
			cmdList.add(cmd107);
			//cmdtaskRespository.save(cmd107);
			
			// 通知处理下一页
			Map<String,String> nextPageMap = new HashMap<>();
			nextPageMap.put(AMZConstant.CMD_KEY_PAGE_TYPE, String.valueOf(AMZConstant.VALUE_PAGE_TYPE_FIRST));
			nextPageMap.put(AMZConstant.CMD_KEY_HTML_FILE_PATH, htmlFilePath);
			cmdTextJson = JSON.toJSONString(nextPageMap);
			AmzCmdtask cmd108 = new AmzCmdtask(CmdType.CMD108, cmdTextJson);
			cmdList.add(cmd108);
			
			cmdtaskRespository.saveAll(cmdList);
			
			return 1;
		} catch (Exception e) {
			log.error("处理子类目下载，异常。cmdText="+cmdText, e);
			return -9999;
		}
	}
	
	/**
	 * 产品列表页下载
	 * @param cmdText
	 * @return
	 */
	public int dealProductListDownload(String cmdText) {
		
		try {
			if(StrUtil.isBlank(cmdText)) {
				log.error("处理产品列表页下载，指令内容为空。");
				return -1;
			}
			
			Map<String,String> cmdTextMap = JSON.parseObject(cmdText, new TypeReference<Map<String, String>>(){});
			if(cmdTextMap == null || cmdTextMap.size() < 1) {
				log.error("处理产品列表页下载，参数为空。cmdText="+cmdText);
				return -2;
			}
			
			String nextPageUrl = cmdTextMap.get(AMZConstant.CMD_KEY_NEXT_PAGE_URL);
			if(StrUtil.isBlank(nextPageUrl)) {
				log.error("处理产品列表页下载，nextPageUrl参数为空。cmdText="+cmdText);
				return -3;
			}
			
			String pageTypeStr = cmdTextMap.get(AMZConstant.CMD_KEY_PAGE_TYPE);
			if(StrUtil.isBlank(pageTypeStr)) {
				log.error("处理产品列表页下载，pageTypeStr参数为空。cmdText="+cmdText);
				return -4;
			}
			
			// TODO 执行下载
			
			// TODO 下载成功，返回html文件路径
			String htmlFilePath = "C:\\Users\\lenovo\\git\\pc_service\\page\\list-page\\Arts & Crafts-page2-123456789.html";
			
			
			List<AmzCmdtask> cmdList = new ArrayList<>();
			
			// 通知处理产品list
			Map<String,String> dataMap = new HashMap<>();
			dataMap.put(AMZConstant.CMD_KEY_PAGE_TYPE, pageTypeStr);
			dataMap.put(AMZConstant.CMD_KEY_HTML_FILE_PATH, htmlFilePath);
			String cmdTextJson = JSON.toJSONString(dataMap);
			AmzCmdtask cmd107 = new AmzCmdtask(CmdType.CMD107, cmdTextJson);
			cmdList.add(cmd107);
			
			// 通知处理下一页
			Map<String,String> nextPageMap = new HashMap<>();
			nextPageMap.put(AMZConstant.CMD_KEY_PAGE_TYPE, pageTypeStr);
			nextPageMap.put(AMZConstant.CMD_KEY_HTML_FILE_PATH, htmlFilePath);
			cmdTextJson = JSON.toJSONString(nextPageMap);
			AmzCmdtask cmd108 = new AmzCmdtask(CmdType.CMD108, cmdTextJson);
			cmdList.add(cmd108);
			
			cmdtaskRespository.saveAll(cmdList);
			
			return 1;
		} catch (Exception e) {
			log.error("处理产品列表页下载，异常。cmdText="+cmdText, e);
			return -9999;
		}
	}
	
	
	
	
}
