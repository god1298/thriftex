/*
 * 文件名称: ThriftClientException.java Copyright 2011-2013 Nali All right reserved.
 */
package com.ximalaya.thrift.client.exception;

/**
 * exception of thrift client
 * 
 * @author ted created on 2013-8-12
 * @author gavin lu
 * @since 1.0
 */
public class ThriftClientException extends RuntimeException {
    /**  */
    private static final long serialVersionUID = 4598677433340242119L;
    public static final ErrorType CONNECTION_ERROR = ErrorType.CONNECTION_ERROR;
    public static final ErrorType APPLICATION_ERROR = ErrorType.APPLICATION_ERROR;
    public static final ErrorType PROTOCAL_ERROR = ErrorType.PROTOCAL_ERROR;
    
    private static enum ErrorType{
    	CONNECTION_ERROR(1), APPLICATION_ERROR(2), PROTOCAL_ERROR(3);
    	private int errorType;
    	ErrorType(int errorType) {
    		this.errorType = errorType;
    	}
    	
    	public int getTypeVal() {
    		return	this.errorType;
    	}
    }
    private ErrorType errorType = APPLICATION_ERROR;
    

    /**
     * 
     */
    public ThriftClientException(ErrorType errorType) {
    	super("errorType: " + errorType);
    	this.errorType = errorType;
    }

//    /**
//     * @param message
//     */
//    public ThriftClientException(String message) {
//        super(message);
//    }
    
    public ThriftClientException(String message, ErrorType errorType) {
        super("errorType: " + errorType + ", " + message);
        this.errorType = errorType;
    }

    /**
     * @param cause
     */
    public ThriftClientException(Throwable cause, ErrorType errorType) {
        super(cause);
        this.errorType = errorType;
    }

//    /**
//     * @param message
//     * @param cause
//     */
//    public ThriftClientException(String message, Throwable cause) {
//        super(message, cause);
//    }
//    
    public ThriftClientException(String message, Throwable cause, ErrorType errorType) {
        super("errorType: " + errorType + ", " + message, cause);
        this.errorType = errorType;
    }

	public ErrorType getErrorType() {
		return errorType;
	}
	
	public int getErrorTypeVal() {
		return errorType.getTypeVal();
	}
}
