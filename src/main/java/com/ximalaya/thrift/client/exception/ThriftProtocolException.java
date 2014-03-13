package com.ximalaya.thrift.client.exception;


public class ThriftProtocolException extends ThriftClientException {
	public ThriftProtocolException() {
		super(PROTOCAL_ERROR);
	}
	
	public ThriftProtocolException(String messgae) {
		super(messgae, PROTOCAL_ERROR);
	}

	public ThriftProtocolException(Throwable casue) {
		super(casue, PROTOCAL_ERROR);
	}
	
	
	public ThriftProtocolException(String messgae, Throwable casue) {
		super(messgae, casue, PROTOCAL_ERROR);
	}
}
