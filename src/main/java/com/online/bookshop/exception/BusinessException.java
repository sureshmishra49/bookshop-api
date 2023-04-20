package com.online.bookshop.exception;


public class BusinessException extends Exception {

    private static final long serialVersionUID = 1L;
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
