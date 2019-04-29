package com.bentest.spiders.proxy;

import java.util.List;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bentest.spiders.entity.AmzProxy;
import com.bentest.spiders.http.HttpRequest;
import com.bentest.spiders.http.HttpResponse;
import com.bentest.spiders.http.HttpUtils;
import com.bentest.spiders.repository.AmzProxyRespository;

import cn.hutool.core.util.StrUtil;

@Service
public class ProxyService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AmzProxyRespository amzProxyRespository;
	
	public List<ProxyInfo> getProxy(){
		
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
			
			
			
		} catch (Exception e) {
			log.error("获取芝麻代理的http代理，异常。count="+count, e);
			return null;
		}
		
	}
	
}
