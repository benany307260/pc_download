package com.bentest.spiders.service.download;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.bentest.spiders.constant.CmdType;
import com.bentest.spiders.dao.CmdMapper;
import com.bentest.spiders.entity.AmzCmdtask;

@Service
public class CmdService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DownloadService downloadService;
	
	@Autowired
	private CmdMapper cmdMapper;
	
	@Async("downloadTaskExecutor")
	public void dealCmdTask(AmzCmdtask cmdTask){
		try {
			// 设置为处理中
			cmdTask.setCmdStatus(1);
			cmdTask.setUpdateTime(new Date());
			// 未处理的指令更新为处理中
			Integer updateRes = cmdMapper.updateCmdStatus(cmdTask.getId(), cmdTask.getCmdStatus(), 0);
			log.info("处理指令任务，指令更新为处理中。updateRes="+updateRes+",cmdId="+cmdTask.getId());
			if(updateRes == null || updateRes < 1) {
				log.error("处理指令任务，指令更新为处理中，更新失败。updateRes="+updateRes+",cmdId="+cmdTask.getId());
				return;
			}
						
			int res = deal(cmdTask);
			if(res > 0) {
				// 处理成功
				cmdTask.setCmdStatus(2);
			}else {
				// 处理失败
				cmdTask.setCmdStatus(3);
			}
			cmdTask.setUpdateTime(new Date());
			
			log.info("处理指令任务，处理结果。cmdId="+cmdTask.getId()+",res="+res);
			
			// 更新指令处理结果
			updateRes = cmdMapper.updateCmdStatus(cmdTask.getId(), cmdTask.getCmdStatus(), 1);
			log.info("处理指令任务，指令任务更新处理结果。updateRes="+updateRes+",cmdId="+cmdTask.getId()+",res="+res);
			if(updateRes == null || updateRes < 1) {
				log.error("处理指令任务，指令任务更新处理结果，更新失败。updateRes="+updateRes+",cmdId="+cmdTask.getId()+",res="+res);
			}
			
			//return true;
		} catch (Exception e) {
			log.error("处理指令任务，异常。", e);
			//return false;
		}
	}
	
	@Async("downloadTaskExecutor")
	public void dealRetryCmdTask(AmzCmdtask cmdTask){
		try {
			int dealCount = cmdTask.getDealCount();
			// 重试次数+1
			dealCount++;
			cmdTask.setDealCount(dealCount);
			// 设置为处理中
			cmdTask.setCmdStatus(1);
			cmdTask.setUpdateTime(new Date());
			// 失败的指令更新为处理中
			Integer updateRes = cmdMapper.updateCmdStatus(cmdTask.getId(), cmdTask.getCmdStatus(), 3, dealCount);
			log.info("处理重试指令任务，指令更新为处理中。updateRes="+updateRes+",cmdId="+cmdTask.getId()+",dealCount="+dealCount);
			if(updateRes == null || updateRes < 1) {
				log.error("处理重试指令任务，指令更新为处理中，更新失败。updateRes="+updateRes+",cmdId="+cmdTask.getId()+",dealCount="+dealCount);
				return;
			}
						
			int res = deal(cmdTask);
			if(res > 0) {
				// 处理成功
				cmdTask.setCmdStatus(2);
			}else {
				// 处理失败
				cmdTask.setCmdStatus(3);
			}
			cmdTask.setUpdateTime(new Date());
			
			log.info("处理重试指令任务，处理结果。cmdId="+cmdTask.getId()+",res="+res);
			
			// 更新指令处理结果
			updateRes = cmdMapper.updateCmdStatus(cmdTask.getId(), cmdTask.getCmdStatus(), 1);
			log.info("处理重试指令任务，指令任务更新处理结果。updateRes="+updateRes+",cmdId="+cmdTask.getId()+",res="+res);
			if(updateRes == null || updateRes < 1) {
				log.error("处理重试指令任务，指令任务更新处理结果，更新失败。updateRes="+updateRes+",cmdId="+cmdTask.getId()+",res="+res);
			}
			
		} catch (Exception e) {
			log.error("处理重试指令任务，异常。", e);
		}
	}
	
	private int deal(AmzCmdtask cmdTask){
		try {
			if(cmdTask == null){
				log.info("指令处理，指令对象为null。");
				return -1;
			}
			
			// 下载子类目html
			if(cmdTask.getCmdType() == CmdType.CMD102) {
				String cmdText = cmdTask.getCmdText();
				int res = downloadService.dealSonDepDownload(cmdText);
				return res;
			}
			// 下载产品详情html
			if(cmdTask.getCmdType() == CmdType.CMD104) {
				String cmdText = cmdTask.getCmdText();
				int res = downloadService.dealProductDownload(cmdText);
				return res;
			}
			// 下载产品列表页html
			if(cmdTask.getCmdType() == CmdType.CMD106) {
				String cmdText = cmdTask.getCmdText();
				int res = downloadService.dealProductListDownload(cmdText);
				return res;
			}
			
			return -1;
			
			// 结果存库
		} catch (Exception e) {
			log.error("处理指令，异常。", e);
			return -9999;
		}
	}
}
