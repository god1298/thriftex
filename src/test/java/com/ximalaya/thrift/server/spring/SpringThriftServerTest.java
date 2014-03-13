/*
 * 文件名称: SpringThriftServerTest.java Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.thrift.server.spring;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ximalaya.thrift.server.ThriftServer;

/**
 * @author ted created on 2013-8-13
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-test-server-context.xml")
public class SpringThriftServerTest {
    @Autowired
    @Qualifier("defaultServer")
    private ThriftServer defaultServer;
    @Autowired
    @Qualifier("customServer")
    private ThriftServer customServer;

    /**
     * @throws java.lang.Exception
     * @author ted created on 2013-8-13
     * @since 1.0
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     * @author ted created on 2013-8-13
     * @since 1.0
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws InterruptedException {
        Assert.assertTrue("defaultServer didn't start.", defaultServer.isRunning());
        Assert.assertEquals(9090, defaultServer.getPort());
        Assert.assertEquals("defaultServer", defaultServer.getServerName());
        Assert.assertEquals(2, defaultServer.getSelectorSize());
        Assert.assertEquals(100, defaultServer.getWorkerSize());
        Assert.assertEquals("compact", defaultServer.getProtocolType());
        Assert.assertEquals("threadselected", defaultServer.getServerType());
        Assert.assertTrue("customServer didn't start.", customServer.isRunning());
        Assert.assertEquals(9091, customServer.getPort());
        Assert.assertEquals("customServer", customServer.getServerName());
        Assert.assertEquals(1, customServer.getSelectorSize());
        Assert.assertEquals(10, customServer.getWorkerSize());
        Assert.assertEquals("json", customServer.getProtocolType());
        Assert.assertEquals("simple", customServer.getServerType());
    }
}
