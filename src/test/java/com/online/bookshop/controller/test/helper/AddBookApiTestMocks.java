package com.online.bookshop.controller.test.helper;

import com.online.bookshop.dto.AddBookRequest;
import com.online.bookshop.dto.BooksInfo;
import com.online.bookshop.dto.BooksRequest;
import com.online.bookshop.dto.BooksResult;
import com.online.bookshop.exception.ErrorCode;

import java.util.ArrayList;
import java.util.List;

import static com.online.bookshop.constants.RequestStatus.FAILURE;

public class AddBookApiTestMocks {

    public static BooksResult mockResponse_AddBookWithOutAnyInput(AddBookRequest request) {
        ErrorCode code = new ErrorCode("INPUT_REQUIRED", "Book addition input is required");
        return populateAndReturnAddBookApiResponse(request, code);
    }

    public static BooksResult mockResponse_AddBookWithOutISBN(AddBookRequest request) {
        ErrorCode code = new ErrorCode("BOOK_ISBN_REQUIRED", "Book ISBN is required");
        return populateAndReturnAddBookApiResponse(request, code);
    }

    public static BooksResult mockResponse_AddBookWithOutBookName(AddBookRequest request) {
        ErrorCode code = new ErrorCode("BOOK_NAME_REQUIRED", "Book Name is required");
        return populateAndReturnAddBookApiResponse(request, code);
    }

    public static BooksResult mockResponse_AddBookWithOutBookDescription(AddBookRequest request) {
        ErrorCode code = new ErrorCode("BOOK_DESCRIPTION_REQUIRED", "Book Description is required");
        return populateAndReturnAddBookApiResponse(request, code);
    }

    public static BooksResult mockResponse_AddBookWithOutBookAuthor(AddBookRequest request) {
        ErrorCode code = new ErrorCode("BOOK_AUTHOR_REQUIRED", "Book Author is required");
        return populateAndReturnAddBookApiResponse(request, code);
    }

    public static BooksResult mockResponse_AddBookWithOutBookType(AddBookRequest request) {
        ErrorCode code = new ErrorCode("BOOK_TYPE_REQUIRED", "Book Type is required");
        return populateAndReturnAddBookApiResponse(request, code);
    }

    public static BooksResult mockResponse_AddBookWithOutBookPrice(AddBookRequest request) {
        ErrorCode code = new ErrorCode("BOOK_PRICE_REQUIRED", "Book Price is required");
        return populateAndReturnAddBookApiResponse(request, code);
    }

    private static BooksResult populateAndReturnAddBookApiResponse(AddBookRequest request, ErrorCode code) {
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
