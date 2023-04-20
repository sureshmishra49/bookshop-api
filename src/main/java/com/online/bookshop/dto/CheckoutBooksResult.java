package com.online.bookshop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.online.bookshop.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckoutBooksResult extends BaseResult {
    private BigDecimal totalPriceBeforeDiscount;
    private BigDecimal totalPriceAfterDiscount;
    private BigDecimal totalDiscountAmount;
    private BigDecimal totalPayableAmount;
    private List<BooksInfo> booksInfo;
    private ErrorCode errorMessage;
    private String status;

    public CheckoutBooksResult() { }

    public CheckoutBooksResult(BigDecimal totalPriceBeforeDiscount, BigDecimal totalPriceAfterDiscount,
                               BigDecimal totalDiscountAmount, BigDecimal totalPayableAmount, List<BooksInfo> booksInfo, ErrorCode errorMessage, String status) {
        this.totalPriceBeforeDiscount = totalPriceBeforeDiscount;
        this.totalPriceAfterDiscount = totalPriceAfterDiscount;
        this.totalDiscountAmount = totalDiscountAmount;
        this.totalPayableAmount = totalPayableAmount;
        this.booksInfo = booksInfo;
        this.errorMessage = errorMessage;
        this.status = status;
    }

    @JsonIgnore
    public CheckoutBooksResult filter() {
        return this;
    }

}
