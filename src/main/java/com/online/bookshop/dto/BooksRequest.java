package com.online.bookshop.dto;

import com.online.bookshop.domain.BooksModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BooksRequest extends BooksModel {
    private Long bookId;
    private String name;
    private String description;
    private String author;
    private String bookType;
    private BigDecimal price;
    private String isbn;
}
