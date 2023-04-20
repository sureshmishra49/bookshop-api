package com.online.bookshop.exception;


public class BusinessException extends Exception {

    private static final long serialVersionUID = 1L;
    private ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

    public BusinessException(String errorCode, String errorMessage) {
        this(new ErrorCode(errorCode, errorMessage));
    }

    public BusinessException(String errorCode, String errorMessage, Exception ex) {
        super(ex);
        this.errorCode = new ErrorCode(errorCode, errorMessage);
    }

    public BusinessException(ErrorCode errorCode, Exception ex) {
        super(ex);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
