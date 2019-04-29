package com.bentest.spiders.proxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bentest.spiders.entity.AmzProxy;
import com.bentest.spiders.http.HttpUtils;
import com.bentest.spiders.repository.AmzProxyRespository;

import cn.hutool.core.util.StrUtil;

@Service
public class ProxyService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AmzProxyRespository amzProxyRespository;
	
	public List<ProxyInfo> getProxy(int count){
		return getProxyForZhiMa(count);
	}
	
	public ProxyInfo getProxy(){
		
		List<ProxyInfo> proxyList = getProxyForZhiMa(1);
		if(CollectionUtils.isEmpty(proxyList)) {
			return null;
		}
		
		return proxyList.get(0);
	}
	
	private List<ProxyInfo> getProxyForZhiMa(int count) {
		if(count < 1) {
			return null;
		}
		List<AmzProxy> amzProxyList = amzProxyRespository.findByProxyType(1);
		if(CollectionUtils.isEmpty(amzProxyList)) {
			return null;
		}
		
		AmzProxy amzProxy = amzProxyList.get(0);
		if(amzProxy == null) {
			return null;
		}
		
		String getUrl = amzProxy.getGetUrl();
		if(StrUtil.isBlank(getUrl)) {
			return null;
		}
		
		try {
			URIBuilder uri = new URIBuilder(getUrl);
			uri.setParameter("num", String.valueOf(count));
			String url = uri.build().toString();
			
			HttpUtils httpUtils = new HttpUtils();
			String response = httpUtils.sendGet(url);
			if(StrUtil.isBlank(response)) {
				return null;
			}
			
			long getTime = System.currentTimeMillis();
			
			log.info("获取芝麻代理的http代理，response="+response);
			
			JSONObject respJsonObj = JSON.parseObject(response);
			Integer code = respJsonObj.getInteger("code");
			if(code != 0) {
				return null;
			}
			
			List<ProxyInfo> proxyList = new ArrayList<>();
			
			JSONArray dataJSONArray = respJsonObj.getJSONArray("data");
			if(dataJSONArray == null || dataJSONArray.size() < 1) {
				return null;
			}
			
			int size = dataJSONArray.size();
	        for (int i = 0; i < size; i++){
	            JSONObject jsonObj = dataJSONArray.getJSONObject(i);
	            if(jsonObj == null) {
	            	continue;
	            }
	            String ip = jsonObj.getString("ip");
	            if(StrUtil.isBlank(ip)) {
	            	continue;
	            }
	            Integer port = jsonObj.getInteger("port");
	            String expireTime = jsonObj.getString("expire_time");
	            
	            ProxyInfo proxy = new ProxyInfo();
	            proxy.setIp(ip);
	            proxy.setPort(port);
	            proxy.setExpireTimeStr(expireTime);
	            proxy.setGetTime(getTime);
	            
	            Date date = DateUtils.parseDate(expireTime, Locale.TRADITIONAL_CHINESE, "yyyy-MM-dd hh:mm:ss");
	            proxy.setExpireTime(date.getTime());
	            proxyList.add(proxy);
	        }
			return proxyList;
		} catch (Exception e) {
			log.error("获取芝麻代理的http代理，异常。count="+count, e);
			return null;
		}
	}
	
	public static void main(String[] args) {
		String json = "{\"code\":0,\"success\":true,\"msg\":\"0\",\"data\":[{\"ip\":\"182.86.190.182\",\"port\":4282,\"expire_time\":\"2019-04-29 22:59:25\"}]}";
		JSONObject jsonObject = JSON.parseObject(json);
		Integer code = jsonObject.getInteger("code");
		System.out.println(code);
		JSONArray dataObj = jsonObject.getJSONArray("data");
		int size = dataObj.size();
        for (int i = 0; i < size; i++){
            JSONObject jsonObj = dataObj.getJSONObject(i);
            String ip = jsonObj.getString("ip");
            System.out.println(ip);
        }
	}
	
}
