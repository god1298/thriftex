package com.ximalaya.thrift.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ximalaya.thrift.client.exception.ThriftApplicationException;
import com.ximalaya.thrift.client.exception.ThriftConnectionException;
import com.ximalaya.thrift.client.exception.ThriftProtocolException;
import com.ximalaya.thrift.util.ThriftexUtils;

/**
 * 提供对所有thrift业务接口的代理,处理某些污染连接的异常,并自动回收连接
 * 
 * @param <T>
 * @author gavin
 * @author ted
 * @since 1.0
 */
public class ThriftInvocationHandler<T> implements InvocationHandler {
    private static Logger logger = LoggerFactory.getLogger(ThriftInvocationHandler.class);
    private ThriftConnection<T> thriftConnection;
    private T client;

    public static <T> Object createProxy(T client, ThriftConnection<T> connection,
        Class<?> ifaceClass) {
        return Proxy.newProxyInstance(ThriftexUtils.getCurrentClassLoader(),
            new Class<?>[] { ifaceClass }, new ThriftInvocationHandler<T>(client, connection,
                ifaceClass));
    }

    private ThriftInvocationHandler(T client, ThriftConnection<T> thriftConnection,
        Class<?> ifaceClass) {
        this.thriftConnection = thriftConnection;
        this.client = client;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean closed = false;
        try {
            return method.invoke(client, args);
        } catch (InvocationTargetException ex) {
            Throwable t = ex.getCause();
            if (t instanceof TTransportException) {
                logger.error(ex.getMessage(), t);
                thriftConnection.getTransport().close();
                thriftConnection.close();
                closed = true;
                throw new ThriftConnectionException(t.getMessage(), t);
            } else {
                logger.error(ex.getMessage(), ex.getCause());
                if (t instanceof TProtocolException) {
                    throw new ThriftProtocolException(t.getMessage(), t);
                } else {
                    throw new ThriftApplicationException(t.getMessage(), t);
                }
            }
        } finally {
            if (!closed) {
                thriftConnection.close();
            }
        }
    }
}
