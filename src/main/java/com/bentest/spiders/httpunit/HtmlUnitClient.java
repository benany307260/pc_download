package com.bentest.spiders.httpunit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.bentest.spiders.http.HttpRequest;
import com.bentest.spiders.http.HttpResponse;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

import cn.hutool.core.util.StrUtil;

public class HtmlUnitClient {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private WebClient wc = null;
	
	public boolean initHttpUnit() {
		HttpUnitConfig config = new HttpUnitConfig();
		return initHttpUnit(config);
	}
	
	public boolean initHttpUnit(String proxyIp, int proxyPort) {
		HttpUnitConfig config = new HttpUnitConfig();
		config.setUseProxy(true);
		config.setProxyIp(proxyIp);
		config.setProxyPort(proxyPort);
		
		return initHttpUnit(config);
	}
	
	public boolean initHttpUnit(HttpUnitConfig config) {
		
		try {
			wc = new WebClient(BrowserVersion.CHROME);
			
			if(config.isUseProxy()) {
				if(StrUtil.isBlank(config.getProxyIp())) {
					log.error("初始化htmlunit，代理ip为空。");
					return false;
				}
				if(config.getProxyPort() == 0) {
					log.error("初始化htmlunit，代理port为0。");
					return false;
				}
				ProxyConfig proxyConfig = new ProxyConfig(config.getProxyIp(), config.getProxyPort());
				wc.getOptions().setProxyConfig(proxyConfig);
			}
			
			wc.getCookieManager().setCookiesEnabled(true);//设置cookie是否可用
			wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true    
			wc.getOptions().setCssEnabled(false); //禁用css支持    
			wc.setAjaxController(new NicelyResynchronizingAjaxController()); // ajax设置
			wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
			wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常    
			wc.getOptions().setTimeout(config.getConnTimeout()); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待    
			
			return true;
		} catch (Exception e) {
			log.error("初始化htmlunit，异常。", e);
			return false;
		}
    }
	
	public HttpResponse get(HttpRequest requestParam) {
		
		if(requestParam == null) {
			log.error("htmlunit获取html页面，request为null。");
			return null;
		}
		if (StringUtils.isBlank(requestParam.getUrl())) {
			log.error("htmlunit获取html页面，url为空。");
			return null;
		}
		if(wc == null) {
			log.error("htmlunit获取html页面，okHttpClient为空。");
			return null;
		}
    	
		log.info("htmlunit获取html页面。"+requestParam.toString());
		
        try {
			/*Page page = wc.getPage(url);
			WebResponse resp = page.getWebResponse();
			String content = resp.getContentAsString();
			System.out.println(content);*/
			
			//HtmlPage page = wc.getPage(requestParam.getUrl());
			Page page = wc.getPage(requestParam.getUrl());
			
			WebResponse response = page.getWebResponse();
			if(response == null) {
				log.info("htmlunit获取html页面，响应为null。url="+requestParam.getUrl());
				return null;
			}
			
			HttpResponse responseParam = new HttpResponse();
			int status = response.getStatusCode();
            responseParam.setCode(status);
            
            Map<String, List<String>> headers = getHeaders(response.getResponseHeaders());
            responseParam.setHeaders(headers);
            log.info("htmlunit获取html页面，响应。statusCode="+status+",headers:"+response.getResponseHeaders().toString()+",url="+requestParam.getUrl());
			
			//String cookies = wc.getCookieManager().getCookies().toString();
			//String pageXml = page.asXml(); //以xml的形式获取响应文本  
			responseParam.setContent(response.getContentAsString());
			return responseParam;
		} catch (Exception e) {
			log.error("htmlunit获取html页面，异常。", e);
			return null;
		}
        finally {
        	if(wc != null) {
        		wc.getCurrentWindow().getJobManager().removeAllJobs();
        		wc.close();
        	}
        }
    }
	
    private Map<String, List<String>> getHeaders(List<NameValuePair> htmlunitHeaders){
    	if(CollectionUtils.isEmpty(htmlunitHeaders)) {
    		return null;
    	}
    	
    	try {
			Map<String, List<String>> headerMap = new HashMap<>();
			
			for (NameValuePair nameValuePair : htmlunitHeaders){
				String headerName = nameValuePair.getName();
				String headerValue = nameValuePair.getValue();
				if(StrUtil.isBlank(headerName)) {
					continue;
				}
				
				List<String> valueList;
				if(headerMap.containsKey(headerName)) {
					valueList = headerMap.get(headerName);
				}
				else {
					valueList = new ArrayList<>();
					headerMap.put(headerName, valueList);
				}
				
				valueList.add(headerValue);
			}
			
			return headerMap;
		} catch (Exception e) {
			log.error("htmlunit获取header，异常。", e);
			return null;
		}
    }

	public static void main(String[] args) {
		
		//String url = "https://httpbin.org/get";
    	String url = "https://nghttp2.org/httpbin/get";
    	//String url = "https://www.baidu.com/";
    	//String url = "https://www.ustc.edu.cn/";
    	//String url = "https://www.yale.edu/";
		
		HtmlUnitClient client = new HtmlUnitClient();
		//client.initHttpUnit();
		client.initHttpUnit("180.104.107.46", 45700);
		
		HttpRequest request = new HttpRequest();
		request.setUrl(url);
		HttpResponse resp = client.get(request);
		
		System.out.println(resp.getContent());
	}
	
}
