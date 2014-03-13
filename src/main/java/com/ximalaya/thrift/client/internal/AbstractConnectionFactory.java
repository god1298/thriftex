package com.ximalaya.thrift.client.internal;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.ximalaya.thrift.client.ClientConfig;
import com.ximalaya.thrift.client.ThriftConnection;

/**
 * 集成commons-pool,生成{@link com.ximalaya.thrift.client.ThriftConnection ThriftConnection}
 * 
 * @param <T>
 * @author ted created on 2014-2-13
 * @since 1.0
 */
abstract class AbstractConnectionFactory<T> extends BasePoolableObjectFactory {
    protected ClientConfig<T> clientConfig;

    public AbstractConnectionFactory() {
    }

    public AbstractConnectionFactory(ClientConfig<T> clientConfig) {
        this.clientConfig = clientConfig;
    }

    protected TTransport createTransport(String host, int port, int timeout) throws Exception {
        TTransport transport = null;
        if (clientConfig.isFramed()) {
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

    public ClientConfig<T> getClientConfig() {
        return clientConfig;
    }

    public void setClientConfig(ClientConfig<T> clientConfig) {
        this.clientConfig = clientConfig;
    }
}
