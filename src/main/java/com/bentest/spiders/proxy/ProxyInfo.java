package com.bentest.spiders.proxy;

public class ProxyInfo {
	
	private Long id;
	
	private String ip;
	
	private int port;
	
	// 提取时间，毫秒数
	private long getTime;
	
	// 失效时间，毫秒数
	private long expireTime;
	
	private String expireTimeStr;
	
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("id=").append(id).append(",")
		.append("ip=").append(ip).append(",")
		.append("port=").append(port).append(",")
		.append("getTime=").append(getTime).append(",")
		.append("expireTime=").append(expireTime).append(",")
		.append("expireTimeStr=").append(expireTimeStr).append(",");
		return sb.toString();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public long getGetTime() {
		return getTime;
	}

	public void setGetTime(long getTime) {
		this.getTime = getTime;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	public String getExpireTimeStr() {
		return expireTimeStr;
	}

	public void setExpireTimeStr(String expireTimeStr) {
		this.expireTimeStr = expireTimeStr;
	}
	
}
