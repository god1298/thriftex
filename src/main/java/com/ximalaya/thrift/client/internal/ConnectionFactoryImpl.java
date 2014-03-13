/*
 * 文件名称: GenericClientFactory.java Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.thrift.client.internal;

import java.lang.reflect.Constructor;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;

import com.ximalaya.thrift.client.ClientConfig;
import com.ximalaya.thrift.client.ThriftConnection;
import com.ximalaya.thrift.client.ThriftConnectionImpl;
import com.ximalaya.thrift.client.ThriftConnectionPoolConfig;
import com.ximalaya.thrift.client.ThriftInvocationHandler;
import com.ximalaya.thrift.util.ThriftexUtils;

/**
 * Generic {@link com.ximalaya.thrift.client.ThriftConnection ThriftConnection} factory.
 * 
 * @author ted created on 2013-8-12
 * @author gavin lu
 * @since 1.0
 */
public class ConnectionFactoryImpl<T> extends AbstractConnectionFactory<T> {
    protected ThriftConnectionPool<T> thriftConnectionPool;

    public ConnectionFactoryImpl() {
    }

    public ConnectionFactoryImpl(ClientConfig<T> clientConfig, ThriftConnectionPoolConfig poolConfig) {
        super(clientConfig);
        this.thriftConnectionPool = new ThriftConnectionPool<T>(this, poolConfig);
    }

    protected T createClient(Class<T> cls, TTransport transport) throws Exception {
        Class<?> params[] = { TProtocol.class };
        Constructor<T> constructor = cls.getConstructor(params);
        TProtocol protocol = createProtocol(transport);
        return (T) constructor.newInstance(protocol);
    }

    protected TProtocol createProtocol(TTransport transport) {
        TProtocol protocol = null;
        switch (clientConfig.getProtocolType()) {
            case compact:
                protocol = new TCompactProtocol(transport);
                break;
            case binary:
                protocol = new TBinaryProtocol(transport);
                break;
            case json:
                protocol = new TJSONProtocol(transport);
                break;
            default:
                throw new IllegalArgumentException("Unknow protocol type "
                    + clientConfig.getProtocolType().name());
        }
        if (clientConfig.isMultiplexed()) {
            String serviceName = ThriftexUtils.getServiceName(clientConfig.getIfaceClass());
            TProtocol tmp = protocol;
            protocol = new TMultiplexedProtocol(tmp, serviceName);
        }
        return protocol;
    }

    public Object makeObject() throws Exception {
        TTransport transport = createTransport(clientConfig.getHost(), clientConfig.getPort(),
            clientConfig.getTimeout());
        T client = createClient(clientConfig.getClientClass(), transport);
        ThriftConnection<T> connection = new ThriftConnectionImpl<T>(transport,
            this.thriftConnectionPool);
        Object proxy = ThriftInvocationHandler.createProxy(client, connection,
            clientConfig.getIfaceClass());
        connection.setClient((T) proxy);
        connection.open();
        return connection;
    }

    public ThriftConnectionPool<T> getThriftGenericClientPool() {
        return thriftConnectionPool;
    }
}
