/*
 * 文件名称: ProtocolType.java Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.thrift;

/**
 * Associate with {@link org.apache.thrift.protocol.TProtocol TProtocol}.
 * <p>
 * Now there are only three kind protocols:
 * <li>{@link org.apache.thrift.protocol.TCompactProtocol TCompactProtocol}</li>
 * <li>{@link org.apache.thrift.protocol.TBinaryProtocol TBinaryProtocol}</li>
 * <li>{@link org.apache.thrift.protocol.TJSONProtocol TJSONProtocol}</li>
 * 
 * @author ted created on 2013-8-13
 * @since 1.0
 */
public enum ProtocolType {
    binary, compact, json
}
