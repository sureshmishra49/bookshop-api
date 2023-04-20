package com.online.bookshop.util;

import com.online.bookshop.constants.RequestStatus;
import com.online.bookshop.dto.*;
import com.online.bookshop.exception.BookShopErrorCodeMapping;
import com.online.bookshop.exception.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CheckoutBooksResultConverter {
    protected static final Logger logger = LoggerFactory.getLogger(CheckoutBooksResultConverter.class);

    public static CheckoutBooksResult populateCheckoutSuccessfulResponse(CheckoutBooksRequest request) {
        CheckoutBooksResult result = new CheckoutBooksResult();
        List<BooksInfo> responseList = new ArrayList<>();
        try {
            request.getBooksList().stream().forEach(bookInfo -> {
                BooksInfo booksInfo = new BooksInfo()
                        .setId(bookInfo.getBookId())
                        .setName(bookInfo.getName())
                        .setDescription(bookInfo.getDescription())
                        .setAuthor(bookInfo.getAuthor())
                        .setBookType(bookInfo.getBookType())
                        .setPrice(bookInfo.getPrice())
                        .setIsbn(bookInfo.getIsbn());
                responseList.add(booksInfo);
            });
        } catch (Exception e) {
            logger.error("Error in setting book data. message -{}, detailedMessage- {} ", e.getMessage(), e);
        }
        return CheckoutBooksResult.builder()
                .booksInfo(responseList)
                .status(RequestStatus.SUCCESS.toString())
                .build();
    }

    public static CheckoutBooksResult populateCheckoutBooksBusinessExceptionResult(CheckoutBooksRequest request, ErrorCode errorCode) {
        CheckoutBooksResult result = new CheckoutBooksResult();
        result.setStatus(RequestStatus.FAILURE.toString());
        result.setErrorMessage(errorCode);
        return result;
    }

    public static CheckoutBooksResult populateSystemErrorInCheckOutApiResult() {
        CheckoutBooksResult result = new CheckoutBooksResult();
        ErrorCode errorCode = BookShopErrorCodeMapping.systemError();
        result.setStatus(RequestStatus.ERROR.toString());
        result.setErrorMessage(errorCode);
        return result;
    }

}
