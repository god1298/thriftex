/*
 * 文件名称: ThriftServiceHost.java Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.thrift.client;

/**
 * object define a thrift service with domain/ip and port
 * 
 * @author ted created on 2013-8-12
 * @since 1.0
 */
public class ThriftServiceHost {
    private String host;
    private int port;

    public ThriftServiceHost() {
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ThriftServiceHost(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
