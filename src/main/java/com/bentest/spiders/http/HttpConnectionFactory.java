package com.bentest.spiders.http;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpConnectionFactory extends BasePooledObjectFactory<HttpConnection> {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
    private AtomicInteger idCount = new AtomicInteger(1);
    
    @Override
    public HttpConnection create() throws Exception {
    	
    	int id = idCount.getAndAdd(1);
    	HttpConnection conn = new HttpConnection(id);
    	
    	log.info("http连接池，创建连接。id="+id);
    	
        return conn;
    }

    @Override
    public PooledObject<HttpConnection> wrap(HttpConnection conn) {
        //包装实际对象
        return new DefaultPooledObject<>(conn);
    }
}