package com.ximalaya.thrift.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;

import com.ximalaya.thrift.util.ThriftExtUtils;

/**
 * 与spring集成,代理业务方法,自动关闭连接
 * 
 * @param <T>
 * @author gavin
 * @author sam
 * @author ted
 * @since 1.0
 */
@Deprecated
public class ThriftConnectionFactoryBean<T> implements FactoryBean<T> {
    private ThriftConnectionFactory<?> clientFactory;
    private Class<?> iface;

    @SuppressWarnings("unchecked")
    @Override
    public T getObject() throws Exception {
        return (T) Proxy.newProxyInstance(ThriftExtUtils.getCurrentClassLoader(),
            new Class[] { iface }, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    ThriftConnection<?> connection = clientFactory.getConnection();
                    try {
                        T client = (T) connection.getClient();
                        return method.invoke(client, args);
                    } catch (InvocationTargetException e) {
                        throw e.getCause();
                    } finally {
                        connection.close();
                    }
                }
            });
    }

    @Override
    public Class<?> getObjectType() {
        return iface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setClientFactory(ThriftConnectionFactory<?> clientFactory) {
        this.clientFactory = clientFactory;
        this.iface = clientFactory.getIfaceClass();
    }
}
