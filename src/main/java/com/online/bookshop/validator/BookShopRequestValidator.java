package com.online.bookshop.validator;

import com.online.bookshop.dto.AddBookRequest;
import com.online.bookshop.dto.BooksRequest;
import com.online.bookshop.dto.CheckoutBooksRequest;
import com.online.bookshop.exception.BookShopErrorCodeMapping;
import com.online.bookshop.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class BookShopRequestValidator {

    public void validateBooksByIdApi(BooksRequest request) throws BusinessException {
        if (request!=null && request.getBookId()==null) throw new BusinessException(BookShopErrorCodeMapping.bookIdRequired());
    }

    public void validateAddBookApi(AddBookRequest request) throws BusinessException {
        if (request==null) throw new BusinessException(BookShopErrorCodeMapping.bookAdditionInputRequired());
        if (isBlank(request.getName())) throw new BusinessException(BookShopErrorCodeMapping.bookNameRequired());
        if (isBlank(request.getAuthor())) throw new BusinessException(BookShopErrorCodeMapping.bookAuthorRequired());
        if (isBlank(request.getDescription())) throw new BusinessException(BookShopErrorCodeMapping.bookDescriptionRequired());
        if (isBlank(request.getBookType())) throw new BusinessException(BookShopErrorCodeMapping.bookTypeRequired());
        if (request.getPrice()==null) throw new BusinessException(BookShopErrorCodeMapping.bookPriceRequired());
        if (isBlank(request.getIsbn())) throw new BusinessException(BookShopErrorCodeMapping.bookISBNRequired());
        if (request.getPrice().compareTo(BigDecimal.ZERO) < 0) throw new BusinessException(BookShopErrorCodeMapping.bookPriceCannotBeNegative());
    }

    public void validateUpdateBookApi(BooksRequest request) throws BusinessException {
        if (request==null) throw new BusinessException(BookShopErrorCodeMapping.inputRequired());
        if (request.getBookId()==null) throw new BusinessException(BookShopErrorCodeMapping.bookIdRequired());
        if (isBlank(request.getIsbn())) throw new BusinessException(BookShopErrorCodeMapping.bookISBNRequired());
    }

    public void validateCheckoutBooksApi(CheckoutBooksRequest request) throws BusinessException {
        if (request==null) throw new BusinessException(BookShopErrorCodeMapping.inputRequired());
        if (CollectionUtils.isEmpty(request.getBooksList())) throw new BusinessException(BookShopErrorCodeMapping.bookListIsEmpty());
    }
}
