package com.bentest.spiders.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bentest.spiders.proxy.ProxyInfo;

public class ProxyQueue {
	
	private static Logger log = LoggerFactory.getLogger(ProxyQueue.class);
	
	/**
	 * 无效代理队列
	 */
	private static ConcurrentLinkedQueue<ProxyInfo> invalidProxyQueue = new ConcurrentLinkedQueue<ProxyInfo>();
	
	public static boolean addInvalidProxy(ProxyInfo proxyInfo) {
		try {
			return invalidProxyQueue.add(proxyInfo);
		} catch (Exception e) {
			log.error("加入过期代理队列，异常。", e);
			return false;
		}
	}
	
	public static ProxyInfo pollInvalidProxy() {
		return invalidProxyQueue.poll();
	}
}
