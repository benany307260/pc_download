package com.bentest.spiders.httppool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.EvictionConfig;
import org.apache.commons.pool2.impl.EvictionPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bentest.spiders.proxy.ProxyInfo;


public class HttpPoolEvictionPolicy<T> implements EvictionPolicy<HttpConnection> {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Override
    public boolean evict(final EvictionConfig config, final PooledObject<HttpConnection> underTest,
            final int idleCount) {
        /*if ((config.getIdleSoftEvictTime() < underTest.getIdleTimeMillis() &&
                config.getMinIdle() < idleCount) ||
                config.getIdleEvictTime() < underTest.getIdleTimeMillis()) {
            return true;
        }*/
        
        HttpConnection conn = underTest.getObject();
        if(conn == null) {
        	log.info("连接池回收，连接对象为null，回收掉。");
        	return true;
        }
        
        if(conn.getFailCount() > 5) {
        	log.info("连接池回收，连接对象失败超5次，回收掉。");
        	return true;
        }
        
        ProxyInfo proxy = conn.getProxy();
        if(proxy == null) {
        	log.info("连接池回收，代理对象为null，回收掉。");
        	return true;
        }
        if(proxy.getExpireTime() < System.currentTimeMillis()) {
        	log.info("连接池回收，代理过期，回收掉。"+proxy.toString());
        	return true;
        }
        return false;
    }
}
