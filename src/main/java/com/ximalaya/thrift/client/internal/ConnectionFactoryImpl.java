/*
 * 文件名称: GenericClientFactory.java Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.thrift.client.internal;

import java.lang.reflect.Constructor;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;

import com.ximalaya.thrift.ProtocolType;
import com.ximalaya.thrift.client.ThriftConnection;
import com.ximalaya.thrift.client.ThriftConnectionImpl;
import com.ximalaya.thrift.client.ThriftConnectionPoolConfig;
import com.ximalaya.thrift.client.ThriftInvocationHandler;

/**
 * Generic {@link com.ximalaya.thrift.client.ThriftConnection ThriftConnection} factory.
 * 
 * @author ted created on 2013-8-12
 * @author gavin lu
 * @since 1.0
 */
public class ConnectionFactoryImpl<T> extends AbstractConnectionFactory<T> {
    private Class<T> clientClass; // which client this factory created
    private Class<?> ifaceClass;
    protected ThriftConnectionPool<T> thriftConnectionPool;

    public ConnectionFactoryImpl() {
    }

    public ConnectionFactoryImpl(String host, int port, int soTimeout, Class<T> clientClass,
        Class<?> ifaceClass, boolean framed, ProtocolType protocolType,
        ThriftConnectionPoolConfig poolConfig) {
        super(host, port, soTimeout, framed, protocolType);
        this.clientClass = clientClass;
        this.ifaceClass = ifaceClass;
        this.thriftConnectionPool = new ThriftConnectionPool<T>(this, poolConfig);
    }

    public Class<T> getClientClass() {
        return clientClass;
    }

    public void setClientClass(Class<T> clazz) {
        this.clientClass = clazz;
    }

    protected T createClient(Class<T> cls, TTransport transport) throws Exception {
        Class<?> params[] = { TProtocol.class };
        Constructor<T> constructor = cls.getConstructor(params);
        TProtocol protocol = createProtocol(transport);
        return (T) constructor.newInstance(protocol);
    }

    protected TProtocol createProtocol(TTransport transport) {
        TProtocol protocol = null;
        switch (protocolType) {
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
            throw new IllegalArgumentException("Unknow protocol type " + protocolType.name());
        }
        return protocol;
    }

    public Object makeObject() throws Exception {
        TTransport transport = createTransport(this.host, this.port, this.soTimeout);
        T client = createClient(clientClass, transport);
        ThriftConnection<T> connection = new ThriftConnectionImpl<T>(transport,
            this.thriftConnectionPool);
        Object proxy = ThriftInvocationHandler.createProxy(client, connection, ifaceClass);
        connection.setClient((T) proxy);
        connection.open();
        return connection;
    }

    public ThriftConnectionPool<T> getThriftGenericClientPool() {
        return thriftConnectionPool;
    }
}
