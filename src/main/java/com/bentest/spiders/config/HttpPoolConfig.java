package com.bentest.spiders.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="httppool")
public class HttpPoolConfig {
	
	/**
	 * 连接最大数
	 */
	private int maxTotal = 1;
	
	/**
	 * 获取连接最大等待时间
	 */
	private int maxWaitMillis = 10000;
	
	/**
	 * 回收资源线程的执行周期，单位毫秒。默认值 -1 ，-1 表示不启用线程回收资源
	 */
	private long timeBetweenEvictionRunsMillis = 5000;

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public int getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(int maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public long getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}
	
}
