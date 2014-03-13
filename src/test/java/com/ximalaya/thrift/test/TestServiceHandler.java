/*
 * 文件名称: TestServiceHandler.java Copyright 2011-2014 Nali All right reserved.
 */
package com.ximalaya.thrift.test;

import org.apache.thrift.TException;

import com.ximalaya.thrift.test.TestService.Iface;

/**
 * @author ted created on 2014-2-13
 * @since 1.0
 */
public class TestServiceHandler implements Iface {
    /**
     * (non-Javadoc)
     * 
     * @see com.ximalaya.thrift.test.TestService.Iface#timeout()
     */
    @Override
    public void maketimeout() throws TException {
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ximalaya.thrift.test.TestService.Iface#put(com.ximalaya.thrift.test.Foo)
     */
    @Override
    public int put(Foo foo) throws FooException, TException {
        throw new FooException(1, "有异常啊");
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ximalaya.thrift.test.TestService.Iface#get(java.lang.String)
     */
    @Override
    public String get(String name) throws TException {
        return name;
    }
}
