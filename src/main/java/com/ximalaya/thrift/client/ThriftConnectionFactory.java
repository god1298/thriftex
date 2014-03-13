package com.ximalaya.thrift.client;

import org.apache.thrift.TServiceClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.ximalaya.thrift.ProtocolType;
import com.ximalaya.thrift.client.internal.ConnectionFactoryImpl;
import com.ximalaya.thrift.client.internal.ThriftConnectionPool;
import com.ximalaya.thrift.util.Defaults;
import com.ximalaya.thrift.util.ThriftExtUtils;

/**
 * thrift连接的工厂类,用来生成{@link com.ximalaya.thrift.client.ThriftConnection ThriftConnection}实例
 * 
 * @param <T>
 * @author gavin
 * @author ted
 * @since 1.0
 */
public class ThriftConnectionFactory<T> implements InitializingBean, DisposableBean {
    private Class<T> clientClass; // which client this factory created
    private Class<?> ifaceClass;
    private boolean framed = Defaults.DEFAULT_FRAMED;// whether or not use TFramedTransport
    private ProtocolType protocolType = Defaults.DEFAULT_PROTOCOL_TYPE;
    private String host = Defaults.DEFAULT_HOST;
    private int port = Defaults.DEFAULT_PORT;
    private int soTimeout = Defaults.DEFAULT_SOTIMEOUT;
    private ThriftConnectionPoolConfig thriftConnectionPoolConfig = new ThriftConnectionPoolConfig();
    private ThriftConnectionPool<T> thriftConnectionPool;

    public Class<T> getClientClass() {
        return clientClass;
    }

    public void setClientClass(Class<T> clientClass) {
        this.clientClass = clientClass;
    }

    public Class<?> getIfaceClass() {
        return ifaceClass;
    }

    public void setIfaceClass(Class<?> ifaceClass) {
        this.ifaceClass = ifaceClass;
    }

    public boolean isFramed() {
        return framed;
    }

    public void setFramed(boolean framed) {
        this.framed = framed;
    }

    public ProtocolType getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(ProtocolType protocolType) {
        this.protocolType = protocolType;
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

    public ThriftConnection<T> getConnection() {
        ThriftConnection<T> connection = thriftConnectionPool.getConnection();
        return connection;
    }

    public ThriftConnectionPoolConfig getThriftClientPoolConfig() {
        return thriftConnectionPoolConfig;
    }

    public void setThriftClientPoolConfig(ThriftConnectionPoolConfig thriftGenericPoolConfig) {
        this.thriftConnectionPoolConfig = thriftGenericPoolConfig;
    }

    @Override
    public void destroy() throws Exception {
        if (thriftConnectionPool != null) {
            thriftConnectionPool.destroy();
        }
    }

    public ThriftConnectionPool<T> getThriftGenericClientPool() {
        return thriftConnectionPool;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.clientClass == null) {
            throw new IllegalArgumentException("Thrift client class is null");
        }
        if (this.clientClass.isAssignableFrom(TServiceClient.class)) {
            throw new IllegalArgumentException("Thrift client class: " + clientClass
                + " not extends " + TServiceClient.class);
        }
        this.ifaceClass = checkAndGetIfaceClass(clientClass, ifaceClass);
        ConnectionFactoryImpl<T> thriftGenericClientFactory = new ConnectionFactoryImpl<T>(host, port,
            soTimeout, clientClass, ifaceClass, framed, protocolType, thriftConnectionPoolConfig);
        this.thriftConnectionPool = thriftGenericClientFactory.getThriftGenericClientPool();
    }

    private static <T> Class<?> checkAndGetIfaceClass(Class<T> clientClass, Class<?> ifaceClass) {
        if (ifaceClass == null) {
            return getIfaceClass(clientClass);
        } else {
            if (!ifaceClass.isInterface()) {
                throw new IllegalArgumentException("Your configured iface class: " + ifaceClass
                    + " is not an interface");
            }
            return ifaceClass;
        }
    }

    private static <T> Class<?> getIfaceClass(Class<T> clientClass) {
        Class<?> ifaceClass = ThriftExtUtils.getIfaceClass(clientClass);
        if (ifaceClass == null) {
            throw new IllegalArgumentException("Can't find Iface interface from the client: "
                + clientClass + ", please check the version of thrift");
        }
        return ifaceClass;
    }
}
