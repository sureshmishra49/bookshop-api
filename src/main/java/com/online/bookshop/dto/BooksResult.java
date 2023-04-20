package com.online.bookshop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.online.bookshop.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Builder
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BooksResult extends BaseResult {
    private String requestId;
    private List<BooksInfo> bookInfo;
    private String status;
    private ErrorCode errorMessage;

    public BooksResult() {
    }

    public BooksResult(String requestId, List<BooksInfo> bookInfo, String status, ErrorCode errorMessage) {
        this.requestId = requestId;
        this.status = status;
        this.bookInfo = bookInfo;
        this.errorMessage = errorMessage;
    }

    public BooksResult(String requestId, String status, ErrorCode errorMessage) {
        this.requestId = requestId;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    @JsonIgnore
    public BooksResult filter() {
        return this;
    }

}
