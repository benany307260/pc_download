package com.bentest.spiders.http;

import java.util.HashMap;
import java.util.Map;

import cn.hutool.http.Header;

public class HttpRequest {
	
	private String url;
	
	private Map<String, String> params = new HashMap<>();
	
	private Map<String, String> headers = new HashMap<>();

	private String charSet = "UTF-8";
	
	private int readTimeout = 20000;
	
	private int connTimeout = 20000;
	
	private boolean useProxy = false;

	private String proxyIp;
	
	private int proxyPort;
	
	private boolean useCaches = false;
	
	public HttpRequest() {
		
	}
	
	public HttpRequest(String url) {
		this.url = url;
	}
	
	private Map<String,String> initHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put(HeaderConstant.NAME_ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
	}

	public boolean isUseCaches() {
		return useCaches;
	}

	public void setUseCaches(boolean useCaches) {
		this.useCaches = useCaches;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
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

	
}
