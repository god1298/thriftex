/*
 * 文件名称: ServerType.java Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.thrift.server;

/**
 * Associate with {@link org.apache.thrift.server.TServer TServer}.
 * <p>
 * There are five kind <tt>TServers</tt>:
 * <li>{@link org.apache.thrift.server.TSimpleServer TSimpleServer}</li>
 * <li>{@link org.apache.thrift.server.TThreadPoolServer TThreadPoolServer}</li>
 * <li>{@link org.apache.thrift.server.TNonblockingServer TNonblockingServer}</li>
 * <li>{@link org.apache.thrift.server.THsHaServer THsHaServer}</li>
 * <li>{@link org.apache.thrift.server.TThreadedSelectorServer TThreadedSelectorServer}</li>
 * 
 * @author ted created on 2013-8-13
 * @since 1.0
 */
public enum ServerType {
    nonblocking, hsha, threadselected, threadpool, simple
}
