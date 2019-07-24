package com.bentest.spiders.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="system")
public class SystemConfig {
	
	/**
	 * 扫描指令Cron表达式，默认每5s一次
	 */
	private String scanCmdCron = "0/5 * * * * ?";
	
	/**
	 * 扫描重试指令Cron表达式，默认每5s一次
	 */
	private String scanRetryCmdCron = "0/5 * * * * ?";
	
	/**
	 * 扫描代理Cron表达式，默认每5s一次
	 */
	private String scanProxyCron = "0/5 * * * * ?";
	
	/**
	 * 服务端节点编号，最大两位
	 */
	private int serverNode;
	
	/**
	 * 服务端区域编号，最大两位
	 */
	private int areaNode;
	
	private String amzUrl;
	
	private String handleCmdTypes;

	private String ossAccessKeyId;
	
	private String ossAccessKeySecret;
	
	private String ossBucketName;
	
	private String ossEndpoint;
	
	private int corePoolSize;
	private int maxPoolSize;
	private int queueCapacity;
	private int keepAliveSeconds;
	
	private int cmdRetryCount;
	
	private int proxyCount;
	
	
	public int getProxyCount() {
		return proxyCount;
	}

	public void setProxyCount(int proxyCount) {
		this.proxyCount = proxyCount;
	}

	public int getCmdRetryCount() {
		return cmdRetryCount;
	}

	public void setCmdRetryCount(int cmdRetryCount) {
		this.cmdRetryCount = cmdRetryCount;
	}

	public String getScanRetryCmdCron() {
		return scanRetryCmdCron;
	}

	public void setScanRetryCmdCron(String scanRetryCmdCron) {
		this.scanRetryCmdCron = scanRetryCmdCron;
	}

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getQueueCapacity() {
		return queueCapacity;
	}

	public void setQueueCapacity(int queueCapacity) {
		this.queueCapacity = queueCapacity;
	}

	public int getKeepAliveSeconds() {
		return keepAliveSeconds;
	}

	public void setKeepAliveSeconds(int keepAliveSeconds) {
		this.keepAliveSeconds = keepAliveSeconds;
	}

	public String getOssEndpoint() {
		return ossEndpoint;
	}

	public void setOssEndpoint(String ossEndpoint) {
		this.ossEndpoint = ossEndpoint;
	}

	public String getOssBucketName() {
		return ossBucketName;
	}

	public void setOssBucketName(String ossBucketName) {
		this.ossBucketName = ossBucketName;
	}

	public String getOssAccessKeySecret() {
		return ossAccessKeySecret;
	}

	public void setOssAccessKeySecret(String ossAccessKeySecret) {
		this.ossAccessKeySecret = ossAccessKeySecret;
	}

	public String getOssAccessKeyId() {
		return ossAccessKeyId;
	}

	public void setOssAccessKeyId(String ossAccessKeyId) {
		this.ossAccessKeyId = ossAccessKeyId;
	}

	public String getAmzUrl() {
		return amzUrl;
	}

	public void setAmzUrl(String amzUrl) {
		this.amzUrl = amzUrl;
	}

	public String getScanCmdCron() {
		return scanCmdCron;
	}

	public void setScanCmdCron(String scanCmdCron) {
		this.scanCmdCron = scanCmdCron;
	}

	public int getServerNode() {
		return serverNode;
	}

	public void setServerNode(int serverNode) {
		this.serverNode = serverNode;
	}

	public int getAreaNode() {
		return areaNode;
	}

	public void setAreaNode(int areaNode) {
		this.areaNode = areaNode;
	}

	public String getScanProxyCron() {
		return scanProxyCron;
	}

	public void setScanProxyCron(String scanProxyCron) {
		this.scanProxyCron = scanProxyCron;
	}

	public String getHandleCmdTypes() {
		return handleCmdTypes;
	}

	public void setHandleCmdTypes(String handleCmdTypes) {
		this.handleCmdTypes = handleCmdTypes;
	}

}
