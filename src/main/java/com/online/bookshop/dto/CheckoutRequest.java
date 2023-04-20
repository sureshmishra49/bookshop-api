package com.online.bookshop.dto;

import com.online.bookshop.domain.BooksModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
public class CheckoutRequest extends BooksModel {
    @Size(min = 1, max = 20)
    @ApiModelProperty(name =  "bookId", required = true)
    private Long bookId;
    private String name;
    private String description;
    private String author;
    @Size(min = 1, max = 255)
    @ApiModelProperty(name =  "bookType", example = "FICTION/COMIC", required = true)
    private String bookType;
    @Size(min = 1, max = 20)
    @ApiModelProperty(name =  "price", required = true)
    private BigDecimal price;
    @Size(min = 1, max = 20)
    @ApiModelProperty(name =  "isbn", required = true)
    private String isbn;
}
