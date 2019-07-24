package com.bentest.spiders.scheduling;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bentest.spiders.config.SystemConfig;
import com.bentest.spiders.dao.CmdMapper;
import com.bentest.spiders.entity.AmzCmdtask;
import com.bentest.spiders.service.download.CmdService;

@Service
public class DealRetryCmdTask {
	
	private static Logger log = LoggerFactory.getLogger(DealRetryCmdTask.class);
	
	@Autowired
	private CmdMapper cmdMapper;
	
	/*@Autowired
	private DownloadService downloadService;*/
	
	@Autowired
	private CmdService cmdService;
	
	@Autowired
    private SystemConfig systemConfig;
	
	private static Integer cmdTaskId = 0;
	
	public void run() {
		
		String cmdTypes = systemConfig.getHandleCmdTypes(); 
		//查询指令表 是否有新增操作
		List<AmzCmdtask> cmdtaskList = cmdMapper.getRetryCmdTask(cmdTypes, systemConfig.getCmdRetryCount());
		if(cmdtaskList == null || cmdtaskList.size() < 1) {
			log.debug("处理失败指令，没有新的指令信息，cmdTaskId:"+cmdTaskId);
			return;
		}
		
		for(AmzCmdtask cmdTask : cmdtaskList) {
			if(cmdTask == null) {
				continue;
			}
			cmdTaskId = cmdTask.getId();
			
			/*// 设置为处理中
			cmdTask.setCmdStatus(1);
			cmdTask.setUpdateTime(new Date());
			// 未处理的指令更新为处理中
			cmdMapper.updateCmdStatus(cmdTask.getId(), cmdTask.getCmdStatus(), 0);*/
			
			cmdService.dealRetryCmdTask(cmdTask);
			
			/*int res = deal(cmdTask);
			if(res > 0) {
				cmdTask.setCmdStatus(2);
			}else {
				cmdTask.setCmdStatus(3);
			}
			cmdTask.setUpdateTime(new Date());
			
			// 更新指令处理结果
			cmdMapper.updateCmdStatus(cmdTask.getId(), cmdTask.getCmdStatus(), 1);*/
		}
	}
	
	
	
	
}
