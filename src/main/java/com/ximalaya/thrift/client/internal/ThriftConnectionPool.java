/*
 * 文件名称: ThriftConnectionPool.java Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.thrift.client.internal;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

import com.ximalaya.thrift.client.ThriftConnection;
import com.ximalaya.thrift.client.exception.ThriftConnectionException;

/**
 * A <tt>ThriftConnectionPool</tt> delegate {@link org.apache.commons.pool.impl.GenericObjectPool
 * GenericObjectPool} to pool {@link org.apache.thrift.TServiceClient TServiceClient} instance.
 * 
 * @author ted created on 2013-8-12
 * @since 1.0
 */
public class ThriftConnectionPool<T> {
    private GenericObjectPool internalPool;

    public ThriftConnectionPool(PoolableObjectFactory factory, GenericObjectPool.Config poolConfig) {
        this.internalPool = new GenericObjectPool(factory, poolConfig);
    }

    @SuppressWarnings("unchecked")
    public ThriftConnection<T> getConnection() {
        try {
            ThriftConnection<T> connection = (ThriftConnection<T>) internalPool.borrowObject();
            return connection;
        } catch (Exception e) {
            throw new ThriftConnectionException("Could not get a connection from the pool", e);
        }
    }

    public void returnBrokenClient(ThriftConnection<T> connection) {
        try {
            internalPool.invalidateObject(connection);
        } catch (Exception e) {
            throw new ThriftConnectionException(
                "Could not return the broken connection to the pool", e);
        }
    }

    public void returnClient(ThriftConnection<T> connection) {
        try {
            internalPool.returnObject(connection);
        } catch (Exception e) {
            throw new ThriftConnectionException("Could not return the connection to the pool", e);
        }
    }

    public int size() {
        return internalPool.getNumIdle() + internalPool.getNumActive();
    }

    public void destroy() {
        try {
            internalPool.close();
        } catch (Exception e) {
            throw new ThriftConnectionException("Could not destroy the connection pool", e);
        }
    }
}
