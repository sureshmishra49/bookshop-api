package com.online.bookshop.dto;

import com.online.bookshop.domain.BooksModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckoutBooksRequest extends BooksModel {
    @ApiModelProperty(name =  "booksInfo", required = true)
    private List<CheckoutRequest> booksList;
}
