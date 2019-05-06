package com.bentest.spiders.httppool;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.bentest.spiders.http.HeaderConstant;
import com.bentest.spiders.http.HttpRequest;
import com.bentest.spiders.http.HttpResponse;
import com.bentest.spiders.http2.OkHttp2UtilForHttps;
import com.bentest.spiders.proxy.ProxyInfo;

import cn.hutool.core.util.StrUtil;

public class HttpConnection {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private int id;
	
	private String userAgent;
	
	private String cookie;
	
	private Map<String,String> cookies = new HashMap<>();
	
	//private String proxyIp;
	
	//private int proxyPort;
	
	private ProxyInfo proxy;
	
	//private HttpUtils httpUtils = new HttpUtils();

	private OkHttp2UtilForHttps httpUtils = null;
	
	public HttpConnection() {
	}
	
	public HttpConnection(int id, String userAgent, ProxyInfo proxy) {
		this.id = id;
		this.userAgent = userAgent;
		this.proxy = proxy;
		
	}
	
	public String sendGetUseH2(String url) {
		if(StrUtil.isBlank(url)) {
			log.error("http连接对象，发送get的https请求，请求url为空。");
			return null;
		}
		if(StrUtil.isBlank(proxy.getIp())) {
			log.error("http连接对象，发送get的https请求，代理ip为空。url="+url);
			return null;
		}
		if(proxy.getPort() == 0) {
			log.error("http连接对象，发送get的https请求，代理port为0。url="+url);
			return null;
		}
		if(StrUtil.isBlank(userAgent)) {
			log.error("http连接对象，发送get的https请求，userAgent为空。url="+url);
			return null;
		}
		if(httpUtils == null) {
			httpUtils = new OkHttp2UtilForHttps();
			httpUtils.initOkHttpClient(proxy.getIp(), proxy.getPort());
		}
		
		/*HttpRequest httpRequest = new HttpRequest();
		httpRequest.setUseProxy(true);
		httpRequest.setProxyIp(proxy.getIp());
		httpRequest.setProxyPort(proxy.getPort());
		httpRequest.setUrl(url);
		
		Map<String, String> headers = httpRequest.getHeadersForH2();
		headers.put(HeaderConstant.NAME_COOKIE, cookie);
		headers.put(HeaderConstant.NAME_USER_AGENT, userAgent);
		
		HttpResponse response = httpUtils.get(httpRequest);*/
		
		HttpResponse response = get(url);
		
		if(response == null) {
			return null;
		}
		if(response.getCode() != HttpURLConnection.HTTP_OK && response.getCode() != HttpURLConnection.HTTP_MOVED_TEMP) {
			log.error("http连接对象，发送get的https请求，响应状态码不为200/302，code="+response.getCode()+",url="+url);
			return null;
		}
		
		setCookie(response);
		
		if(response.getCode() == HttpURLConnection.HTTP_OK) {
			return response.getContent();
		}
		if(response.getCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
			String location = getRedirectUrl(response);
			String content = redirectGet(location);
			return content;
		}
		return null;
	}
	
	/**
	 * 获取重定向url
	 */
	private String getRedirectUrl(HttpResponse response) {
		if(CollectionUtils.isEmpty(response.getHeaders())) {
			return null;
		}
		if(CollectionUtils.isEmpty(response.getHeaders().get("location"))) {
			return null;
		}
		String location = response.getHeaders().get("location").get(0);
		if(StrUtil.isBlank(location)) {
			return null;
		}
		String url = response.getScheme() + "://" + response.getHost() + location;
		return url;
	}
	
	private String redirectGet(String url) {
		if(StrUtil.isBlank(url)) {
			return null;
		}
		HttpResponse response = get(url);
		if(response == null) {
			return null;
		}
		if(response.getCode() == HttpURLConnection.HTTP_OK) {
			return response.getContent();
		}
		if(response.getCode() == HttpURLConnection.HTTP_MOVED_TEMP) {
			log.info("http连接对象，请求后重定向，又返回302，不再继续。"+response.toString());
		}
		return null;
	}
	
	private HttpResponse get(String url) {
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setUseProxy(true);
		httpRequest.setProxyIp(proxy.getIp());
		httpRequest.setProxyPort(proxy.getPort());
		httpRequest.setUrl(url);
		
		Map<String, String> headers = httpRequest.getHeadersForH2();
		headers.put(HeaderConstant.NAME_COOKIE, cookie);
		headers.put(HeaderConstant.NAME_USER_AGENT, userAgent);
		
		HttpResponse response = httpUtils.get(httpRequest);
		return response;
	}
	
	private boolean setCookie(HttpResponse response) {
		Map<String, List<String>> responseHeaders = response.getHeaders();
		if(CollectionUtils.isEmpty(responseHeaders)) {
			log.info("http连接对象，根据响应设置cookie，响应无header。");
			return true;
		}
		
		List<String> cookieList = responseHeaders.get(HeaderConstant.NAME_SET_COOKIE);
		if(CollectionUtils.isEmpty(cookieList)) {
			return true;
		}
		try {
			for(String cookie : cookieList) {
				if(StrUtil.isBlank(cookie)) {
					log.info("http连接对象，根据响应设置cookie，cookie为空。");
					continue;
				}
				
				String[] cookieParams = cookie.split(";");
				if(cookieParams == null || cookieParams.length < 1) {
					log.info("http连接对象，根据响应设置cookie，cookie参数为空。");
					continue;
				}
				
				String cookieNameValue = cookieParams[0];
				if(StrUtil.isBlank(cookieNameValue)) {
					log.info("http连接对象，根据响应设置cookie，cookie名称和值为空。");
					continue;
				}
				
				String[] cookieNameValueArray = cookieNameValue.split("=");
				if(cookieNameValueArray == null || cookieNameValueArray.length < 2) {
					log.info("http连接对象，根据响应设置cookie，cookie名称和值数组为空或长度不正确。");
					continue;
				}
				
				this.cookies.put(cookieNameValueArray[0], cookieNameValueArray[1]);
			}
			
			if(CollectionUtils.isEmpty(cookies)) {
				log.info("http连接对象，根据响应设置cookie，cookiemap为空。");
				return false;
			}
			
			String cookieStr = createCookiesString(cookies);
			if(StrUtil.isBlank(cookieStr)) {
				log.info("http连接对象，根据响应设置cookie，cookie字符串为空。");
				return false;
			}
			
			this.cookie = cookieStr;
			
			return true;
		} catch (Exception e) {
			log.error("http连接对象，根据响应设置cookie，异常。", e);
			return false;
		}
	}
	
	private String createCookiesString(Map<String, String> cookieMap) {
		try {
			StringBuffer sb = new StringBuffer();
			for (Map.Entry<String, String> entry : cookieMap.entrySet()) {
				sb.append(" ").append(entry.getKey()).append("=").append(entry.getValue()).append(";");
			}
			String cookieStr = sb.substring(1, sb.length()-1);
			return cookieStr;
		} catch (Exception e) {
			log.error("http连接对象，创建cookie字符串，异常。", e);
			return null;
		}
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

	/*public HttpUtils getHttpUtils() {
		return httpUtils;
	}

	public void setHttpUtils(HttpUtils httpUtils) {
		this.httpUtils = httpUtils;
	}*/
}