package com.bentest.spiders.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bentest.spiders.dao.AmzProxyMapper;
import com.bentest.spiders.proxy.ProxyInfo;
import com.bentest.spiders.queue.ProxyQueue;

@Service
public class DealProxyTask {
	
	private static Logger log = LoggerFactory.getLogger(DealProxyTask.class);
	
	@Autowired
	private AmzProxyMapper amzProxyMapper;
	
	
	public void run() {
		
		updateExpiredProxy();
		
		updateInvalidProxy();
		
	}
	
	/**
	 * 更新过期代理
	 */
	private void updateExpiredProxy() {
		//查询过期代理
		Integer count = amzProxyMapper.getExpireProxyCount();
		if(count == null || count < 1) {
			return;
		}
		log.info("处理代理，过期失效代理数："+count);
		
		Integer resultCount = amzProxyMapper.updateProxyNoUse();
		log.info("处理代理，更新过期失效代理为不可用，更新数："+resultCount);
	}
	
	/**
	 * 更新无效代理
	 */
	private void updateInvalidProxy() {
		StringBuffer proxyIdSb = new StringBuffer();
		// 循环次数
		int loopCount = 500;
		int i = 0;
		while(i < loopCount) {
			ProxyInfo proxy = ProxyQueue.pollInvalidProxy();
			if(proxy == null) {
				break;
			}
			if(proxyIdSb.length() > 0) {
				proxyIdSb.append(",");
			}
			proxyIdSb.append(proxy.getId());
		}
		
		if(proxyIdSb.length() < 0) {
			return;
		}
		
		Integer resultCount = amzProxyMapper.updateProxyNoUse(proxyIdSb.toString());
		log.info("处理代理，更新失效代理为不可用，更新数："+resultCount);
	}
	
}
