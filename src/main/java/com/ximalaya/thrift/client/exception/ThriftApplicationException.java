package com.ximalaya.thrift.client.exception;

public class ThriftApplicationException extends ThriftClientException {
	public ThriftApplicationException() {
		super(APPLICATION_ERROR);
	}
	
	public ThriftApplicationException(String messgae) {
		super(messgae, APPLICATION_ERROR);
	}

	public ThriftApplicationException(Throwable casue) {
		super(casue, APPLICATION_ERROR);
	}
	
	
	public ThriftApplicationException(String messgae, Throwable casue) {
		super(messgae, casue, APPLICATION_ERROR);
	}
}
