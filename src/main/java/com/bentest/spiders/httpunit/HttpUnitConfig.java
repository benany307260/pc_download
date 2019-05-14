package com.bentest.spiders.httpunit;

import com.bentest.spiders.httppool.BrowserType;

public class HttpUnitConfig {
	
	private String charSet = "UTF-8";
	
	private int readTimeout = 30000;
	
	private int connTimeout = 30000;
	
	private int writeTimeout = 30000;
	
	private boolean useProxy = false;

	private String proxyIp;
	
	private int proxyPort;
	
	private boolean useCaches = false;
	
	private BrowserType browserType = BrowserType.Chrome;
	
	public HttpUnitConfig() {
		
	}

	public BrowserType getBrowserType() {
		return browserType;
	}

	public void setBrowserType(BrowserType browserType) {
		this.browserType = browserType;
	}

	public int getWriteTimeout() {
		return writeTimeout;
	}

	public void setWriteTimeout(int writeTimeout) {
		this.writeTimeout = writeTimeout;
	}

	public String getCharSet() {
		return charSet;
	}

	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public int getConnTimeout() {
		return connTimeout;
	}

	public void setConnTimeout(int connTimeout) {
		this.connTimeout = connTimeout;
	}

	public boolean isUseProxy() {
		return useProxy;
	}

	public void setUseProxy(boolean useProxy) {
		this.useProxy = useProxy;
	}

	public String getProxyIp() {
		return proxyIp;
	}

	public void setProxyIp(String proxyIp) {
		this.proxyIp = proxyIp;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public boolean isUseCaches() {
		return useCaches;
	}

	public void setUseCaches(boolean useCaches) {
		this.useCaches = useCaches;
	}
	
}
