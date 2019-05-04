package com.bentest.spiders.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bentest.spiders.dao.AmzProxyMapper;

@Service
public class DealProxyTask {
	
	private static Logger log = LoggerFactory.getLogger(DealProxyTask.class);
	
	@Autowired
	private AmzProxyMapper amzProxyMapper;
	
	
	public void run() {
		
		//查询指令表 是否有新增操作
		Integer count = amzProxyMapper.getExpireProxyCount();
		if(count == null || count < 1) {
			return;
		}
		log.info("处理代理，失效代理数："+count);
		
		Integer resultCount = amzProxyMapper.updateProxyNoUse();
		log.info("处理代理，更新失效代理为不可用，更新数："+resultCount);
	}
	
}
