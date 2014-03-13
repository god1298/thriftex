/*
 * 文件名称: MultiplexedThriftServer.java Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.thrift.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportFactory;

import com.ximalaya.thrift.util.ThriftExtUtils;

/**
 * @author ted created on 2013-9-5
 * @since 1.0
 */
public class MultiplexedThriftServer extends AbstractThriftServer {
    private List<Object> handlers;
    private List<Class<?>> handlerInterfaces;
    private List<Class<TProcessor>> processorClasses;

    public void setHandlers(List<Object> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.handlers == null || this.handlers.isEmpty()) {
            throw new IllegalArgumentException("hander is null or empty!");
        }
        if (this.handlerInterfaces != null && this.handlerInterfaces.size() != this.handlers.size()) {
            throw new IllegalArgumentException(
                "handers size and handlerInterfaces size is not equal!");
        }
        if (this.processorClasses != null && this.processorClasses.size() != this.handlers.size()) {
            throw new IllegalArgumentException(
                "handers size and processorClasses size is not equal!");
        }
        if (this.handlerInterfaces == null) {
            this.handlerInterfaces = new ArrayList<Class<?>>(handlers.size());
            for (int i = 0; i < handlers.size(); i++) {
                this.handlerInterfaces.add(null);
            }
        }
        if (this.processorClasses == null) {
            this.processorClasses = new ArrayList<Class<TProcessor>>(handlers.size());
            for (int i = 0; i < handlers.size(); i++) {
                this.processorClasses.add(null);
            }
        }
        Object handler = null;
        for (int i = 0; i < handlers.size(); i++) {
            handler = handlers.get(i);
            Class<?> handlerClass = handler.getClass();
            Class<?> ifaceClazz = ThriftExtUtils.getIfaceClass(handlerClass);
            Class<?> serviceClazz = ThriftExtUtils.getServiceClass(ifaceClazz);
            if (this.handlerInterfaces.get(i) == null) {
                if (ifaceClazz == null) {
                    throw new IllegalArgumentException(
                        "handler interface is null and the system can't resovle it");
                }
                this.handlerInterfaces.add(i, ifaceClazz);
            }
            if (this.processorClasses.get(i) == null) {
                if (serviceClazz == null) {
                    throw new IllegalArgumentException(
                        "Service class is null and the system can't resovle it");
                }
                Class<TProcessor> processorClass = ThriftExtUtils.getProcessorClass(serviceClazz);
                if (processorClass == null) {
                    throw new IllegalArgumentException(
                        "Processor class is null and the system can't resovle it");
                }
                this.processorClasses.add(i, processorClass);
            }
        }
    }

    @Override
    protected void createServer() throws Exception {
        TMultiplexedProcessor multiplexedProcessor = new TMultiplexedProcessor();
        for (int i = 0; i < handlers.size(); i++) {
            TProcessor processor = createProcessor(processorClasses.get(i),
                handlerInterfaces.get(i), handlers.get(i));
            Class<?> serviceClass = ThriftExtUtils.getServiceClass(handlerInterfaces.get(i));
            multiplexedProcessor.registerProcessor(serviceClass.getSimpleName().toLowerCase()
                .replace("service", ""), processor);
        }
        TProtocolFactory factory = createProtocolFactory();
        switch (serverType) {
        case threadselected:
            org.apache.thrift.server.TThreadedSelectorServer.Args threadSelectedArgs = new org.apache.thrift.server.TThreadedSelectorServer.Args(
                (TNonblockingServerTransport) transport);
            threadSelectedArgs.protocolFactory(factory);
            threadSelectedArgs.processor(multiplexedProcessor);
            threadSelectedArgs.workerThreads(workerSize);
            threadSelectedArgs.selectorThreads(selectorSize);
            TTransportFactory threadSelectedTransportFactory = new TFramedTransport.Factory();
            threadSelectedArgs.transportFactory(threadSelectedTransportFactory);
            server = new TThreadedSelectorServer(threadSelectedArgs);
            break;
        case hsha:
            org.apache.thrift.server.THsHaServer.Args hshaArgs = new org.apache.thrift.server.THsHaServer.Args(
                (TNonblockingServerTransport) transport);
            hshaArgs.protocolFactory(factory);
            hshaArgs.processor(multiplexedProcessor);
            hshaArgs.workerThreads(workerSize);
            TTransportFactory hshaTransportFactory = new TFramedTransport.Factory();
            hshaArgs.transportFactory(hshaTransportFactory);
            server = new THsHaServer(hshaArgs);
            break;
        case nonblocking:
            org.apache.thrift.server.TNonblockingServer.Args nonblockingArgs = new org.apache.thrift.server.TNonblockingServer.Args(
                (TNonblockingServerTransport) transport);
            nonblockingArgs.protocolFactory(factory);
            nonblockingArgs.processor(multiplexedProcessor);
            TTransportFactory nonblockingTransportFactory = new TFramedTransport.Factory();
            nonblockingArgs.transportFactory(nonblockingTransportFactory);
            server = new TNonblockingServer(nonblockingArgs);
            break;
        case threadpool:
            org.apache.thrift.server.TThreadPoolServer.Args threadPoolArgs = new org.apache.thrift.server.TThreadPoolServer.Args(
                transport);
            threadPoolArgs.protocolFactory(factory);
            threadPoolArgs.processor(multiplexedProcessor);
            threadPoolArgs.maxWorkerThreads(workerSize);
            threadPoolArgs.minWorkerThreads(workerSize);
            server = new TThreadPoolServer(threadPoolArgs);
            break;
        case simple:
            org.apache.thrift.server.TSimpleServer.Args simpleArgs = new org.apache.thrift.server.TSimpleServer.Args(
                transport);
            simpleArgs.protocolFactory(factory);
            simpleArgs.processor(multiplexedProcessor);
            server = new TSimpleServer(simpleArgs);
            break;
        default:
            throw new IllegalArgumentException("Unknow server type " + serverType);
        }
    }
}
