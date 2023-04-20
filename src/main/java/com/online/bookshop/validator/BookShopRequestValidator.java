package com.online.bookshop.validator;

import com.online.bookshop.dto.AddBookRequest;
import com.online.bookshop.exception.BookShopErrorCodeMapping;
import com.online.bookshop.exception.BusinessException;
import com.online.bookshop.dto.BooksRequest;
import com.online.bookshop.dto.CheckoutBooksRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class BookShopRequestValidator {
    private static final Logger logger = LoggerFactory.getLogger(BookShopRequestValidator.class);

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
    }

    public void validateUpdateBookApi(BooksRequest request) throws BusinessException {
        if (request==null) throw new BusinessException(BookShopErrorCodeMapping.inputRequired());
        if (request.getBookId()==null) throw new BusinessException(BookShopErrorCodeMapping.bookIdRequired());
        if (isBlank(request.getIsbn())) throw new BusinessException(BookShopErrorCodeMapping.bookISBNRequired());
    }

    public void validateCheckoutBooksApi(CheckoutBooksRequest request) throws BusinessException {
        if (request==null) throw new BusinessException(BookShopErrorCodeMapping.inputRequired());
    }

    public boolean isNumeric(String strNum) {
        if (isBlank(strNum)) return false;
        try {
            Long d = Long.parseLong(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
