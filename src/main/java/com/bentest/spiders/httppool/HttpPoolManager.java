package com.bentest.spiders.httppool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bentest.spiders.config.HttpPoolConfig;
import com.bentest.spiders.spring.SpringUtil;

public class HttpPoolManager {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static HttpPoolManager httpPoolManager = new HttpPoolManager();
	
	private HttpConnectionPool connectionPool = null;
	
	private HttpPoolManager() {}
	
	public static HttpPoolManager getInstance() {
		return httpPoolManager;
	}
	
	public boolean initHttpPool() {
		try {
			HttpConnectionFactory orderFactory = new HttpConnectionFactory();
			GenericObjectPoolConfig<HttpConnection> config = new GenericObjectPoolConfig<HttpConnection>();
			
			HttpPoolConfig HttpPoolConfig = (HttpPoolConfig)SpringUtil.getBean("httpPoolConfig");
			
			config.setMaxTotal(HttpPoolConfig.getMaxTotal());
			//设置获取连接超时时间
			config.setMaxWaitMillis(HttpPoolConfig.getMaxWaitMillis());
			config.setEvictionPolicy(new HttpPoolEvictionPolicy<HttpConnection>());
			config.setTimeBetweenEvictionRunsMillis(HttpPoolConfig.getTimeBetweenEvictionRunsMillis());
			
			connectionPool = new HttpConnectionPool(orderFactory, config);
			return true;
		} catch (Exception e) {
			log.error("连接池管理，初始化，异常。", e);
			return false;
		}
	}
	
	public HttpConnection getConnection() {
		if(connectionPool == null) {
			log.error("连接池管理，获取连接，连接池对象为null。");
			return null;
		}
		try {
			return connectionPool.borrowObject();
		} catch (Exception e) {
			log.error("连接池管理，获取连接，异常。", e);
			return null;
		}
	}
	
	public void returnConnection(HttpConnection conn) {
		if(connectionPool == null) {
			log.error("连接池管理，返还连接，连接池对象为null。");
		}
		if(conn == null) {
			log.info("连接池管理，返还连接，连接对象为null。");
			return;
		}
		try {
			connectionPool.returnObject(conn);
		} catch (Exception e) {
			log.error("连接池管理，返还连接，异常。", e);
		}
	}
}
