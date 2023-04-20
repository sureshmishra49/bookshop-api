package com.online.bookshop.controller.test.helper;

import com.online.bookshop.dto.BooksResult;

import static com.online.bookshop.constants.RequestStatus.NO_DATA_FOUND;
import static java.util.Collections.EMPTY_LIST;

public class GetBooksApiTestMocks {

    public static BooksResult mockResponse_NoRecordFoundInDB() {
        return BooksResult.builder()
                .bookInfo(EMPTY_LIST)
                .status(NO_DATA_FOUND.toString())
                .build();
    }
}
