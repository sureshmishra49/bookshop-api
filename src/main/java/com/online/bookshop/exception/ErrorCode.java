package com.online.bookshop.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.slf4j.MDC;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorCode implements Serializable {
    private final String mdcTokenKey = "clientId";
    private String code;
    private String message;
    private String traceId;

    public ErrorCode(String code, String message) {
        setCode(code);
        setMessage(message);
        if (MDC.getMDCAdapter() != null && MDC.get(mdcTokenKey) != null) {
            setTraceId(MDC.get(mdcTokenKey));
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorCode errorCode = (ErrorCode) o;

        if (!code.equals(errorCode.code)) return false;
        return message.equals(errorCode.message);

    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + message.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ErrorCode{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
