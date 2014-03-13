/*
 * 文件名称: ClientConfig.java Copyright 2011-2014 Ximalaya All right reserved.
 */
package com.ximalaya.thrift.client;

import com.ximalaya.thrift.ProtocolType;
import com.ximalaya.thrift.util.Defaults;

/**
 * client attributes
 * 
 * @author ted
 */
public class ClientConfig<T> {
    private Class<T> clientClass;
    private Class<?> ifaceClass;
    private boolean framed = Defaults.DEFAULT_FRAMED;
    private ProtocolType protocolType = Defaults.DEFAULT_PROTOCOL_TYPE;
    private boolean multiplexed = Defaults.DEFAULT_MULTIPLEXED;
    private String host;
    private int port;
    private int timeout;

    public Class<T> getClientClass() {
        return clientClass;
    }

    public void setClientClass(Class<T> clientClass) {
        this.clientClass = clientClass;
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

    public boolean isMultiplexed() {
        return multiplexed;
    }

    public void setMultiplexed(boolean multiplexed) {
        this.multiplexed = multiplexed;
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

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Class<?> getIfaceClass() {
        return ifaceClass;
    }

    public void setIfaceClass(Class<?> ifaceClass) {
        this.ifaceClass = ifaceClass;
    }
}
