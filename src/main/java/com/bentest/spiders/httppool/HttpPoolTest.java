package com.bentest.spiders.httppool;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class HttpPoolTest {
	public static void main(String[] args) throws Exception {
		HttpConnectionFactory orderFactory = new HttpConnectionFactory();
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(5);
        //设置获取连接超时时间
        config.setMaxWaitMillis(1000);
        HttpConnectionPool connectionPool = new HttpConnectionPool(orderFactory, config);
        for (int i = 0; i < 7; i++) {
        	HttpConnection o = connectionPool.borrowObject();
            System.out.println("brrow a connection: " + o +" active connection:"+connectionPool.getNumActive());
            //connectionPool.returnObject(o);
        }
	}
}
