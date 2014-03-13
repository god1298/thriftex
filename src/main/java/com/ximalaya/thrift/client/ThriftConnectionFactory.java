package com.ximalaya.thrift.client;

import org.apache.thrift.TServiceClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.ximalaya.thrift.client.internal.ConnectionFactoryImpl;
import com.ximalaya.thrift.client.internal.ThriftConnectionPool;
import com.ximalaya.thrift.util.ThriftexUtils;

/**
 * thrift连接的工厂类,用来生成{@link com.ximalaya.thrift.client.ThriftConnection ThriftConnection}实例
 * 
 * @param <T>
 * @author gavin
 * @author ted
 * @since 1.0
 */
public class ThriftConnectionFactory<T> implements InitializingBean, DisposableBean {
    private ThriftConnectionPoolConfig thriftConnectionPoolConfig = new ThriftConnectionPoolConfig();
    private ThriftConnectionPool<T> thriftConnectionPool;
    private ClientConfig<T> clientConfig;

    public ClientConfig<T> getClientConfig() {
        return clientConfig;
    }

    public void setClientConfig(ClientConfig<T> clientConfig) {
        this.clientConfig = clientConfig;
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
        if (clientConfig.getClientClass() == null) {
            throw new IllegalArgumentException("Thrift client class is null");
        }
        if (clientConfig.getClientClass().isAssignableFrom(TServiceClient.class)) {
            throw new IllegalArgumentException("Thrift client class: "
                + clientConfig.getClientClass() + " not extends " + TServiceClient.class);
        }
        clientConfig.setIfaceClass(checkAndGetIfaceClass(clientConfig.getClientClass(),
            clientConfig.getIfaceClass()));
        ConnectionFactoryImpl<T> thriftGenericClientFactory = new ConnectionFactoryImpl<T>(
            clientConfig, thriftConnectionPoolConfig);
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
        Class<?> ifaceClass = ThriftexUtils.getIfaceClass(clientClass);
        if (ifaceClass == null) {
            throw new IllegalArgumentException("Can't find Iface interface from the client: "
                + clientClass + ", please check the version of thrift");
        }
        return ifaceClass;
    }
}
