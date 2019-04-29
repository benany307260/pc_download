package com.bentest.spiders.http;

import com.bentest.spiders.proxy.ProxyInfo;

public class HttpConnection {
	
	private int id;
	
	private String userAgent;
	
	private String cookie;
	
	//private String proxyIp;
	
	//private int proxyPort;
	
	private ProxyInfo proxy;
	
	private HttpUtils httpUtils = new HttpUtils();

	
	public HttpConnection() {
	}
	
	public HttpConnection(int id, String userAgent, ProxyInfo proxy) {
		this.id = id;
		this.userAgent = userAgent;
		this.proxy = proxy;
		
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setUseProxy(true);
		httpRequest.setProxyIp(proxy.getIp());
		httpRequest.setProxyPort(proxy.getPort());
		httpRequest.getHeaders().put(HeaderConstant.NAME_USER_AGENT, userAgent);
		httpUtils.setHttpRequest(httpRequest);
	}
	
	public String toString() {
		String str = ""
			+ "id=" + id
			+ "userAgent=" + userAgent
			+ "cookie=" + cookie
			//+ "proxyIp=" + proxyIp
			//+ "proxyPort=" + proxyPort
			;
		return str;
	}
	
	public ProxyInfo getProxy() {
		return proxy;
	}

	public void setProxy(ProxyInfo proxy) {
		this.proxy = proxy;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public HttpUtils getHttpUtils() {
		return httpUtils;
	}

	public void setHttpUtils(HttpUtils httpUtils) {
		this.httpUtils = httpUtils;
	}
}
