/*
 * 文件名称: ServiceClientWrapper.java Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.thrift.client;

import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.ximalaya.thrift.client.exception.ThriftClientException;
import com.ximalaya.thrift.client.internal.ThriftConnectionPool;

/**
 * A {@link org.apache.thrift.TServiceClient TServiceClient} wrapper for release
 * client to pool. Use {@link ThriftConnectionImpl#close()
 * ClientWrapper.releaseClient()} in finally block. Use {@link ThriftConnectionImpl#getClient()
 * ClientWrapper.getClient()} to get the
 * true {@link org.apache.thrift.TServiceClient TServiceClient} instance.
 * 
 * @author ted created on 2013-8-12
 * @author gavin
 * @since 1.0
 */
public class ThriftConnectionImpl<T> implements ThriftConnection<T> {
    // private T client; // the true client wrappered
    private ThriftConnectionPool<T> pool; // the pool client belong to
    private TTransport transport;
    private volatile T client;

    public ThriftConnectionImpl(TTransport transport, ThriftConnectionPool<T> pool) {
        this.pool = pool;
        this.transport = transport;
    }

    /**
     * return the client to pool, if client's transport is closed, it will be
     * returnd as a broken client
     * 
     * @author ted created on 2013-8-13
     */
    public void close() {
        if (pool != null) {
            try {
                if (isOpen()) {
                    pool.returnClient(this);
                }
            } catch (Exception ex) {
                pool.returnBrokenClient(this);
            }
        }
        if (pool == null) {
            if (this.transport != null) {
                this.transport.close();
            }
        }
    }

    public void setClientPool(ThriftConnectionPool<T> pool) {
        this.pool = pool;
    }

    public TTransport getTransport() {
        return transport;
    }

    public void setTransport(TTransport transport) {
        this.transport = transport;
    }

    public ThriftConnectionPool<T> getClientPool() {
        return pool;
    }

    @Override
    public boolean isOpen() {
        return this.transport.isOpen();
    }

    @Override
    public void open() throws ThriftClientException {
        try {
            this.transport.open();
        } catch (TTransportException e) {
            throw new ThriftClientException(e.getMessage(), e,
                ThriftClientException.CONNECTION_ERROR);
        }
    }

    public void setClient(T client) {
        this.client = client;
    }

    @Override
    public T getClient() {
        return client;
    }
}