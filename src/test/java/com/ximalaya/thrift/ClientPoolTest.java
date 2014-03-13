/*
 * 文件名称: ClientPoolTest.java Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.thrift;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ximalaya.thrift.client.ThriftConnection;
import com.ximalaya.thrift.client.ThriftConnectionFactory;
import com.ximalaya.thrift.test.TestService.Iface;

/**
 * @author ted created on 2013-8-12
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-test-client-context.xml")
public class ClientPoolTest {
    @Autowired
    private ThriftConnectionFactory<Iface> factory;

    /**
     * @throws java.lang.Exception
     * @author ted created on 2013-8-12
     * @since 1.0
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     * @author ted created on 2013-8-12
     * @since 1.0
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws Exception {
        ThriftConnection<Iface> connection = factory.getConnection();
        Assert.assertTrue(connection.isOpen());
        connection.getClient().get("name");
        connection.close();
        Assert.assertTrue(connection.isOpen());
        connection.getTransport().close();
        Assert.assertFalse(connection.isOpen());
    }
}
