package com.online.bookshop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BooksInfo extends BaseResult {
    private Long id;
    private String name;
    private String description;
    private String author;
    private String bookType;
    private BigDecimal price;
    private String isbn;
    private String createdDate;

    public BooksInfo() {
    }

    public BooksInfo(Long  id, String name, String description, String author, String bookType, BigDecimal price, String isbn, String createdDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
        this.bookType = bookType;
        this.price = price;
        this.isbn = isbn;
        this.createdDate = createdDate;
    }

    @JsonIgnore
    public BooksInfo filter() {
        return this;
    }

}
