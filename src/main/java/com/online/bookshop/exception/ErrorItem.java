package com.online.bookshop.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

@JsonSerialize
public class ErrorItem implements Serializable, Comparable {

    private String errorMessage;
    private String propertyName;

    public ErrorItem(String propertyName, String errorMessage) {
        this.errorMessage = errorMessage;
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorItem errorItem = (ErrorItem) o;

        if (!errorMessage.equals(errorItem.errorMessage)) return false;
        return propertyName.equals(errorItem.propertyName);

    }

    @Override
    public int hashCode() {
        int result = errorMessage.hashCode();
        result = 31 * result + propertyName.hashCode();
        return result;
    }

    public int compareTo(Object o) {
        ErrorItem errorItem = (ErrorItem) o;
        return this.propertyName.compareTo(errorItem.propertyName);
    }
}
