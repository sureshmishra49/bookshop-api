package com.online.bookshop.controller.test.helper;

import com.online.bookshop.dto.BooksInfo;
import com.online.bookshop.dto.BooksRequest;
import com.online.bookshop.dto.BooksResult;
import com.online.bookshop.exception.ErrorCode;

import java.util.ArrayList;
import java.util.List;

import static com.online.bookshop.constants.RequestStatus.FAILURE;

public class UpdateBookApiTestMocks {

    public static BooksResult mockResponse_UpdateBookSystemErrorResponse(BooksRequest request) {
        ErrorCode code = new ErrorCode("SYSTEM_ERROR", "System error");
        return populateAndReturnAddBookApiResponse(request, code);
    }

    public static BooksResult mockResponse_UpdateBookWithOutAnyInput(BooksRequest request) {
        ErrorCode code = new ErrorCode("INPUT_REQUIRED", "Book addition input is required");
        return populateAndReturnAddBookApiResponse(request, code);
    }

    public static BooksResult mockResponse_UpdateBookWithOutISBN(BooksRequest request) {
        ErrorCode code = new ErrorCode("BOOK_ISBN_REQUIRED", "Book ISBN is required");
        return populateAndReturnAddBookApiResponse(request, code);
    }

    public static BooksResult mockResponse_UpdateBookWithOutBookId(BooksRequest request) {
        ErrorCode code = new ErrorCode("BOOK_ID_REQUIRED", "Book Id is required");
        return populateAndReturnAddBookApiResponse(request, code);
    }

    private static BooksResult populateAndReturnAddBookApiResponse(BooksRequest request, ErrorCode code) {
        List<BooksInfo> responseList = new ArrayList<>();
        BooksInfo booksInfo = new BooksInfo()
                .setName(request.getName())
                .setDescription(request.getDescription())
                .setAuthor(request.getAuthor())
                .setBookType(request.getBookType())
                .setPrice(request.getPrice())
                .setIsbn(request.getIsbn());
        responseList.add(booksInfo);
        return BooksResult.builder()
                .bookInfo(responseList)
                .status(FAILURE.toString())
                .errorMessage(code)
                .build();
    }
}
