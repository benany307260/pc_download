package com.bentest.spiders.httppool;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class HttpConnectionPool extends GenericObjectPool<HttpConnection> {

    public HttpConnectionPool(PooledObjectFactory<HttpConnection> factory) {
        super(factory);
    }

    public HttpConnectionPool(PooledObjectFactory<HttpConnection> factory, GenericObjectPoolConfig<HttpConnection> config) {
        super(factory, config);
    }

    public HttpConnectionPool(PooledObjectFactory<HttpConnection> factory, GenericObjectPoolConfig<HttpConnection> config, AbandonedConfig abandonedConfig) {
        super(factory, config, abandonedConfig);
    }
}