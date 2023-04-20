package com.online.bookshop.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.List;

@JsonSerialize
public class ErrorResponse implements Serializable {
    private List<ErrorItem> errors;
    private String message;
    private String developerMessage;
    private String errorCode;
    private Integer httpCode;
    private ErrorCode error;

    public ErrorResponse(String message, Integer httpCode) {
        this.message = message;
        this.httpCode = httpCode;
    }

    public ErrorResponse(String message, List<ErrorItem> errors, String developerMessage, String errorCode) {
        this.message = message;
        this.errors = errors;
        this.errorCode = errorCode;
        this.developerMessage = developerMessage;
    }
    public ErrorResponse(String message, ErrorCode error, String developerMessage, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
        this.error = error;
        this.developerMessage = developerMessage;
    }

    public List<ErrorItem> getErrors() {
        return errors;
    }

    public String getMessage() {
        return message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public ErrorCode getError() {
        return error;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }
}
