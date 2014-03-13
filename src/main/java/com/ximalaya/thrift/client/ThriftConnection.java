package com.ximalaya.thrift.client;

import org.apache.thrift.transport.TTransport;

/**
 * 抽象thrift的连接
 * <P>
 * <ul>
 * <li>包装了{@link org.apache.thrift.transport.TTransport TTransport}</li>
 * <li>提供对连接池回收的支持</li>
 * </ul>
 * 
 * @param <T>
 * @author gavin
 * @author ted
 * @since 1.0
 */
public interface ThriftConnection<T> {
    /**
     * 关闭连接
     */
    void close();

    /**
     * 察看是否打开连接
     * 
     * @return 打开:true,其他:false
     */
    boolean isOpen();

    /**
     * 打开连接
     */
    void open();

    /**
     * 获得委托的连接对象
     * 
     * @return
     */
    TTransport getTransport();

    /**
     * 获取client实例
     * 
     * @return
     */
    T getClient();

    /**
     * 设置client实例
     * 
     * @param client
     */
    void setClient(T client);
}
