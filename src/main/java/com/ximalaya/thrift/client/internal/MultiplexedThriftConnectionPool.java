package com.ximalaya.thrift.client.internal;

import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;

import com.ximalaya.thrift.client.ThriftConnection;
import com.ximalaya.thrift.client.exception.ThriftConnectionException;

public class MultiplexedThriftConnectionPool<T> {
	private GenericKeyedObjectPool internalPool;

	public MultiplexedThriftConnectionPool(KeyedPoolableObjectFactory factory,
			GenericKeyedObjectPool.Config poolConfig) {
		this.internalPool = new GenericKeyedObjectPool(factory, poolConfig);
	}

	@SuppressWarnings("unchecked")
	public ThriftConnection<T> getConnection(String serviceName) {
		try {
			ThriftConnection<T> connection = (ThriftConnection<T>) internalPool
					.borrowObject(serviceName);
			return connection;
		} catch (Exception e) {
			throw new ThriftConnectionException(
					"Could not get a client from the pool", e);
		}
	}

	public void returnBrokenClient(String serviceName, final ThriftConnection<T> wrapper) {
		try {
			internalPool.invalidateObject(serviceName, wrapper);
		} catch (Exception e) {
			throw new ThriftConnectionException(
					"Could not return the broken client to the pool", e);
		}
	}

	public void returnClient(String serviceName, final ThriftConnection<T> wrapper) {
		try {
			internalPool.returnObject(serviceName, wrapper);
		} catch (Exception e) {
			throw new ThriftConnectionException(
					"Could not return the client to the pool", e);
		}
	}

	public int size() {
		return internalPool.getNumIdle() + internalPool.getNumActive();
	}
	
	public int size(String serviceName) {
		return internalPool.getNumActive(serviceName) + internalPool.getNumIdle(serviceName);
 	}

	public void setMaxActive(int maxActive) {
		internalPool.setMaxActive(maxActive);
	}

	public void closeAll() {
		try {
			internalPool.close();
		} catch (Exception e) {
			throw new ThriftConnectionException("Could not destroy the pool", e);
		}
	}
}
