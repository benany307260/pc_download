package com.montnets.pc_service.scheduling;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.montnets.pc_service.constant.CmdType;
import com.montnets.pc_service.dao.CmdMapper;
import com.montnets.pc_service.entity.AmzCmdtask;
import com.montnets.pc_service.listpage.ProductListService;
import com.montnets.pc_service.service.department.AmzDepService;
import com.montnets.pc_service.service.product.AmzProductService;

@Service
public class DealCmdTask {
	
	private static Logger log = LoggerFactory.getLogger(DealCmdTask.class);
	
	@Autowired
	private CmdMapper cmdMapper;
	
	@Autowired
	private AmzDepService amzDepService;
	
	@Autowired
	private AmzProductService productService;
	
	@Autowired
	private ProductListService productListService;
	
	private static Integer cmdTaskId = 0;
	
	public void run() {
		
		//查询指令表 是否有新增操作
		List<AmzCmdtask> cmdtaskList = cmdMapper.getCmdTask(cmdTaskId);
		if(cmdtaskList == null || cmdtaskList.size() < 1) {
			log.info("处理指令，没有新的指令信息，cmdTaskId:"+cmdTaskId);
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
			
			// 
			if(cmdTask.getCmdType() == CmdType.CMD101) {
				String cmdText = cmdTask.getCmdText();
				int res = amzDepService.dealRootDep(cmdText);
				return res;
			}
			if(cmdTask.getCmdType() == CmdType.CMD103) {
				String cmdText = cmdTask.getCmdText();
				int res = amzDepService.dealSonDep(cmdText);
				return res;
			}
			if(cmdTask.getCmdType() == CmdType.CMD105) {
				String cmdText = cmdTask.getCmdText();
				int res = productService.dealProduct(cmdText);
				return res;
			}
			if(cmdTask.getCmdType() == CmdType.CMD107) {
				String cmdText = cmdTask.getCmdText();
				int res = productListService.dealProductList(cmdText);
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
