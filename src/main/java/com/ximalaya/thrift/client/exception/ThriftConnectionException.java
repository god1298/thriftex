package com.ximalaya.thrift.client.exception;


public class ThriftConnectionException extends ThriftClientException{
	public ThriftConnectionException() {
		super(CONNECTION_ERROR);
	}
	
	public ThriftConnectionException(String messgae) {
		super(messgae, CONNECTION_ERROR);
	}

	public ThriftConnectionException(Throwable casue) {
		super(casue, CONNECTION_ERROR);
	}
	
	
	public ThriftConnectionException(String messgae, Throwable casue) {
		super(messgae, casue, CONNECTION_ERROR);
	}
}
