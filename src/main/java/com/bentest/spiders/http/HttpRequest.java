package com.bentest.spiders.http;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.CollectionUtils;

public class HttpRequest {
	
	private String url;
	
	private Map<String, String> params = new HashMap<>();
	
	private Map<String, String> headers = null;
	
	private Map<String, String> headersForH2 = initHeaderForH2();

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
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("url").append("=").append(url).append(",");
		sb.append("useProxy").append("=").append(useProxy).append(",");
		sb.append("proxyIp").append("=").append(proxyIp).append(",");
		sb.append("proxyPort").append("=").append(proxyPort).append(",");
		if(!CollectionUtils.isEmpty(headersForH2)) {
			sb.append("headersForH2").append("：").append(headersForH2.toString()).append(",");
		}
		if(!CollectionUtils.isEmpty(params)) {
			sb.append("params").append("：").append(params.toString()).append(",");
		}
		return sb.toString();
	}
	
	public Map<String,String> initHeaderForH2() {
		Map<String, String> headers = new HashMap<>();
		//headers.put(HeaderConstant.NAME_METHOD, "GET");
		//headers.put(HeaderConstant.NAME_SCHEME, "https");
		headers.put(HeaderConstant.NAME_ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		headers.put(HeaderConstant.NAME_ACCEPT_ENCODING, "gzip, deflate, br");
		headers.put(HeaderConstant.NAME_ACCEPT_LANGUAGE, "en-US,en;q=0.8");
		headers.put(HeaderConstant.NAME_CACHE_CONTROL, "no-cache");
		headers.put(HeaderConstant.NAME_UPGRADE_INSECURE_REQUESTS, "1");
		headers.put(HeaderConstant.NAME_USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
		return headers;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
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

	public Map<String, String> getHeadersForH2() {
		return headersForH2;
	}

	public void setHeadersForH2(Map<String, String> headersForH2) {
		this.headersForH2 = headersForH2;
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
