package com.online.bookshop.util;

import com.online.bookshop.constants.RequestStatus;
import com.online.bookshop.dto.AddBookRequest;
import com.online.bookshop.dto.BooksInfo;
import com.online.bookshop.dto.BooksResult;
import com.online.bookshop.exception.BookShopErrorCodeMapping;
import com.online.bookshop.exception.ErrorCode;
import com.online.bookshop.repository.entities.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class BooksResultConverter {

    public static BooksResult populateAllBooksSuccessfulResponse(List<BooksInfo> responseList) {
        return BooksResult.builder()
                .bookInfo(responseList)
                .status(RequestStatus.SUCCESS.toString())
                .build();
    }

    public static BooksResult populateSingleBookSuccessfulResponse(Book book) {
        return getBooksResult(book);
    }

    private static BooksResult getBooksResult(Book book) {
        List<BooksInfo> responseList = new ArrayList<>();
        BooksInfo booksInfo = new BooksInfo()
                .setId(book.getId())
                .setName(book.getName())
                .setDescription(book.getDescription())
                .setAuthor(book.getAuthor())
                .setBookType(book.getBookType())
                .setPrice(book.getPrice())
                .setIsbn(book.getIsbn())
                .setCreatedDate(book.getCreatedDate()!=null?book.getCreatedDate().toString():EMPTY);
        responseList.add(booksInfo);

        return BooksResult.builder()
                .bookInfo(responseList)
                .status(RequestStatus.SUCCESS.toString())
                .build();
    }

    public static BooksResult populateBookAdditionSuccessfulResponse(Book book) {
        return getBooksResult(book);
    }

    public static BooksResult populateBookAdditionFailedResponse(AddBookRequest book, ErrorCode errorCode) {
        List<BooksInfo> responseList = new ArrayList<>();
        BooksInfo booksInfo = new BooksInfo()
                .setName(book.getName())
                .setDescription(book.getDescription())
                .setAuthor(book.getAuthor())
                .setBookType(book.getBookType())
                .setPrice(book.getPrice())
                .setIsbn(book.getIsbn());
        responseList.add(booksInfo);

        return BooksResult.builder()
                .bookInfo(responseList)
                .status(RequestStatus.FAILURE.toString())
                .errorMessage(errorCode)
                .build();
    }


    public static BooksResult populateBookDeletionSuccessfulResponse(Book book) {
        List<BooksInfo> responseList = new ArrayList<>();
        BooksInfo booksInfo = new BooksInfo()
                .setId(book.getId());
        responseList.add(booksInfo);
        return BooksResult.builder()
                .bookInfo(responseList)
                .status(RequestStatus.SUCCESS.toString())
                .build();
    }

    public static BooksResult populateNoDataFoundResponse() {
        return BooksResult.builder()
                .bookInfo(Collections.EMPTY_LIST)
                .status(RequestStatus.NO_DATA_FOUND.toString())
                .build();
    }

    public static BooksResult populateBusinessExceptionResponse(ErrorCode errorCode) {
        return BooksResult.builder()
                .bookInfo(Collections.EMPTY_LIST)
                .status(RequestStatus.FAILURE.toString())
                .errorMessage(errorCode)
                .build();
    }

    public static BooksResult populateSystemErrorResult() {
        BooksResult result = new BooksResult();
        ErrorCode errorCode = BookShopErrorCodeMapping.systemError();
        result.setStatus(RequestStatus.ERROR.toString());
        result.setErrorMessage(errorCode);
        return result;
    }
}
