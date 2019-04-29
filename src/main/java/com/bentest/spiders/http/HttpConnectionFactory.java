package com.bentest.spiders.http;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bentest.spiders.proxy.ProxyInfo;
import com.bentest.spiders.proxy.ProxyService;

public class HttpConnectionFactory extends BasePooledObjectFactory<HttpConnection> {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProxyService proxyService;
	
    private AtomicInteger idCount = new AtomicInteger(1);
    
    @Override
    public HttpConnection create() throws Exception {
    	
    	int id = idCount.getAndAdd(1);
    	String ua = UAUtils.getRandomUA();
    	ProxyInfo proxy = proxyService.getProxy();
    	if(proxy == null) {
    		throw new Exception("http连接池，创建连接，获取不到代理IP对象");
    	}
    	
    	HttpConnection conn = new HttpConnection(id, ua, proxy);
    	
    	log.info("http连接池，创建连接。"+conn.toString());
    	
        return conn;
    }

    @Override
    public PooledObject<HttpConnection> wrap(HttpConnection conn) {
        //包装实际对象
        return new DefaultPooledObject<>(conn);
    }
}