package com.bentest.spiders.scheduling;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bentest.spiders.config.SystemConfig;
import com.bentest.spiders.constant.CmdType;
import com.bentest.spiders.dao.CmdMapper;
import com.bentest.spiders.entity.AmzCmdtask;
import com.bentest.spiders.service.download.DownloadService;

@Service
public class DealCmdTask {
	
	private static Logger log = LoggerFactory.getLogger(DealCmdTask.class);
	
	@Autowired
	private CmdMapper cmdMapper;
	
	@Autowired
	private DownloadService downloadService;
	
	@Autowired
    private SystemConfig systemConfig;
	
	private static Integer cmdTaskId = 0;
	
	public void run() {
		
		String cmdTypes = systemConfig.getHandleCmdTypes();
		//查询指令表 是否有新增操作
		List<AmzCmdtask> cmdtaskList = cmdMapper.getCmdTask(cmdTaskId, cmdTypes);
		if(cmdtaskList == null || cmdtaskList.size() < 1) {
			log.debug("处理指令，没有新的指令信息，cmdTaskId:"+cmdTaskId);
			return;
		}
		
		for(AmzCmdtask cmdTask : cmdtaskList) {
			if(cmdTask == null) {
				continue;
			}
			cmdTaskId = cmdTask.getId();
			
			// 设置为处理中
			cmdTask.setCmdStatus(1);
			cmdTask.setUpdateTime(new Date());
			// 未处理的指令更新为处理中
			cmdMapper.updateCmdStatus(cmdTask.getId(), cmdTask.getCmdStatus(), 0);
			
			int res = deal(cmdTask);
			if(res > 0) {
				cmdTask.setCmdStatus(2);
			}else {
				cmdTask.setCmdStatus(3);
			}
			cmdTask.setUpdateTime(new Date());
			
			// 更新指令处理结果
			cmdMapper.updateCmdStatus(cmdTask.getId(), cmdTask.getCmdStatus(), 1);
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
