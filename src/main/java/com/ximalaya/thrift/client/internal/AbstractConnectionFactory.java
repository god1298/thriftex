package com.ximalaya.thrift.client.internal;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.ximalaya.thrift.ProtocolType;
import com.ximalaya.thrift.client.ThriftConnection;

/**
 * 集成commons-pool,生成{@link com.ximalaya.thrift.client.ThriftConnection ThriftConnection}
 * 
 * @param <T>
 * @author ted created on 2014-2-13
 * @since 1.0
 */
abstract class AbstractConnectionFactory<T> extends BasePoolableObjectFactory {
    protected boolean framed;
    protected ProtocolType protocolType;
    protected String host;
    protected int port;
    protected int soTimeout;

    public AbstractConnectionFactory() {
    }

    public AbstractConnectionFactory(String host, int port, int soTimeout, boolean framed,
        ProtocolType protocolType) {
        this.host = host;
        this.port = port;
        this.framed = framed;
        this.protocolType = protocolType;
        this.soTimeout = soTimeout;
    }

    public boolean isFramedTransport() {
        return framed;
    }

    public void setFramedTransport(boolean framedTransport) {
        this.framed = framedTransport;
    }

    public String getProtocolType() {
        return protocolType.name();
    }

    public void setProtocolType(String protocolType) {
        this.protocolType = ProtocolType.valueOf(protocolType);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    protected TTransport createTransport(String host, int port, int timeout) throws Exception {
        TTransport transport = null;
        if (framed) {
            transport = new TFramedTransport(new TSocket(host, port, timeout));
        } else {
            transport = new TSocket(host, port, timeout);
        }
        return transport;
    }

    @SuppressWarnings("unchecked")
    public void destroyObject(Object o) throws Exception {
        if (o instanceof ThriftConnection) {
            ThriftConnection<T> connection = (ThriftConnection<T>) o;
            if (connection.isOpen()) {
                connection.getTransport().close();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public boolean validateObject(Object o) {
        if (o instanceof ThriftConnection) {
            return ((ThriftConnection<T>) o).isOpen();
        }
        return false;
    }
}
