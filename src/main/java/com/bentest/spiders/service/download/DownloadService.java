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
import com.bentest.spiders.aliyunoss.OSSService;
import com.bentest.spiders.config.SystemConfig;
import com.bentest.spiders.constant.AMZConstant;
import com.bentest.spiders.constant.CmdType;
import com.bentest.spiders.entity.AmzCmdtask;
import com.bentest.spiders.entity.AmzDepartment;
import com.bentest.spiders.entity.AmzProduct;
import com.bentest.spiders.httppool.HttpConnection;
import com.bentest.spiders.httppool.HttpPoolManager;
import com.bentest.spiders.repository.AmzCmdtaskRespository;
import com.bentest.spiders.util.GetIncrementId;

import cn.hutool.core.util.StrUtil;

@Service
public class DownloadService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/*@Autowired
	private AmzDepartmentRespository amzDepartmentRespository;*/
	
	@Autowired
	AmzCmdtaskRespository cmdtaskRespository;
	
	@Autowired
    private SystemConfig systemConfig;
	
	@Autowired
	private OSSService ossService;
	
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
			
			String url = parentDep.getUrl();
			if(StrUtil.isBlank(url)) {
				log.error("处理子类目下载，类目url为空。cmdText="+cmdText);
				return -3;
			}
			
			if(StrUtil.isBlank(parentDep.getUrlDomain())) {
				log.error("处理子类目下载，类目url域名为空。cmdText="+cmdText);
				url = AMZConstant.AMZ_US_DOMAIN + url;
			}else {
				url = parentDep.getUrlDomain() + url;
			}
			
			// 最终请求url
			url = "http://" + url;
			
			// TODO 执行下载，如何保证连续性
			HttpConnection conn = HttpPoolManager.getInstance().getConnection();
			String resp = conn.send(url);
			HttpPoolManager.getInstance().returnConnection(conn);
			if(StrUtil.isBlank(resp)) {
				log.error("处理子类目下载，返回内容为空。cmdText="+cmdText);
				return -4;
			}
			
			//String htmlFilePath = "C:\\Users\\lenovo\\git\\pc_service\\page\\list-page\\Automotive\\Automotive-1-123456789.html";
			//String htmlFilePath = "C:\\Users\\lenovo\\git\\pc_service\\page\\list-page\\Automotive\\Tools & Equipment-1-123456789.html";
			long id = GetIncrementId.getInstance().getCount(systemConfig.getServerNode(), systemConfig.getAreaNode());
			String fileName = parentDep.getDepId() + "/" + id;
			boolean upResult = ossService.uploadString(fileName, resp);
			// 失败
			if(!upResult) {
				log.error("处理子类目下载，上传oss失败。cmdText="+cmdText+",resp="+resp);
				return -5;
			}
			
			String htmlFilePath = fileName;
			
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
	 * 产品详情页下载
	 * @param cmdText
	 * @return
	 */
	public int dealProductDownload(String cmdText) {
		
		try {
			if(StrUtil.isBlank(cmdText)) {
				log.error("处理产品详情页下载，指令内容为空。");
				return -1;
			}
			
			AmzProduct product = JSON.parseObject(cmdText, AmzProduct.class);
			if(product == null) {
				log.error("处理产品详情页下载，产品对象为null。cmdText="+cmdText);
				return -2;
			}
			if(StrUtil.isBlank(product.getProdUrl())) {
				log.error("处理产品详情页下载，url为null。cmdText="+cmdText);
				return -3;
			}
			
			// TODO 执行下载
			
			// TODO 下载成功，返回html文件路径
			//String htmlFilePath = "C:\\Users\\lenovo\\git\\pc_service\\page\\list-page\\Automotive\\Tools & Equipment-2-B003WXGLS2.html";
			String htmlFilePath = "C:\\Users\\lenovo\\git\\pc_service\\page\\list-page\\Automotive\\Tools & Equipment-2-B07C2XLR3J.html";
			//String htmlFilePath = "C:\\Users\\lenovo\\git\\pc_service\\page\\list-page\\Art Paper-123456789.html";
			//String htmlFilePath = "C:\\Users\\lenovo\\git\\pc_service\\page\\list-page\\Artist Trading Cards-123456789.html";
			
			
			//String htmlFilePath = "F:\\study\\amz\\git\\pc_service\\page\\list-page\\Arts & Crafts-123456789.html";
			
			product.setHtmlFilePath(htmlFilePath);
			
			List<AmzCmdtask> cmdList = new ArrayList<>();
			
			// 通知处理产品详情html
			String cmdTextJson = JSON.toJSONString(product);
			AmzCmdtask cmd105 = new AmzCmdtask(CmdType.CMD105, cmdTextJson);
			cmdList.add(cmd105);
			
			cmdtaskRespository.saveAll(cmdList);
			
			return 1;
		} catch (Exception e) {
			log.error("处理产品详情页下载，异常。cmdText="+cmdText, e);
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
			//String htmlFilePath = "C:\\Users\\lenovo\\git\\pc_service\\page\\list-page\\Automotive\\Automotive-2-123456789.html";
			String htmlFilePath = "C:\\Users\\lenovo\\git\\pc_service\\page\\list-page\\Automotive\\Tools & Equipment-2-123456789.html";
			
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
