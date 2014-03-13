/*
 * 文件名称: ClientTest.java Copyright 2011-2014 Nali All right reserved.
 */
package com.ximalaya.thrift;

import org.apache.thrift.TException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ximalaya.thrift.client.ThriftConnection;
import com.ximalaya.thrift.client.ThriftConnectionFactory;
import com.ximalaya.thrift.client.exception.ThriftApplicationException;
import com.ximalaya.thrift.client.exception.ThriftConnectionException;
import com.ximalaya.thrift.test.Foo;
import com.ximalaya.thrift.test.TestService.Client;
import com.ximalaya.thrift.test.TestService.Iface;

/**
 * @author ted created on 2014-2-13
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-test-client-context.xml")
public class ClientTest {
    @Autowired
    private ThriftConnectionFactory<Client> thriftConnectionFactory;

    /**
     * @throws java.lang.Exception
     * @author ted created on 2014-2-13
     * @since 1.0
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     * @author ted created on 2014-2-13
     * @since 1.0
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMaketimeout() throws TException {
        ThriftConnection<Client> connection = thriftConnectionFactory.getConnection();
        Iface client = connection.getClient();
        try {
            client.maketimeout();
        } catch (Exception e) {
            org.junit.Assert.assertTrue(e instanceof ThriftConnectionException);
        }
        org.junit.Assert.assertFalse(connection.isOpen());
    }

    @Test
    public void testGet() throws TException {
        ThriftConnection<Client> connection = thriftConnectionFactory.getConnection();
        Iface client = connection.getClient();
        String result = client.get("name");
        org.junit.Assert.assertEquals("name", result);
        try {
            client.get(null);
        } catch (Exception e) {
            org.junit.Assert.assertTrue(e instanceof ThriftApplicationException);
        }
    }

    @Test
    public void testPut() throws TException {
        ThriftConnection<Client> connection = thriftConnectionFactory.getConnection();
        Iface client = connection.getClient();
        Foo foo = new Foo();
        foo.setId(1l);
        foo.setCount(1);
        foo.setName("na");
        foo.setDeleted(false);
        try {
            client.put(foo);
        } catch (Exception e) {
            org.junit.Assert.assertTrue(e instanceof ThriftApplicationException);
        }
    }
}
