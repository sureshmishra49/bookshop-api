package com.online.bookshop.helper;

import com.online.bookshop.constants.BookType;
import com.online.bookshop.constants.RequestStatus;
import com.online.bookshop.dto.AddBookRequest;
import com.online.bookshop.dto.BooksRequest;
import com.online.bookshop.dto.CheckoutBooksRequest;
import com.online.bookshop.dto.CheckoutBooksResult;
import com.online.bookshop.exception.BookShopErrorCodeMapping;
import com.online.bookshop.exception.BusinessException;
import com.online.bookshop.repository.BookRepository;
import com.online.bookshop.repository.entities.Book;
import com.online.bookshop.util.CheckoutBooksResultConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import static com.online.bookshop.constants.AppConstant.comicDiscountPercentage;
import static com.online.bookshop.constants.AppConstant.fictionDiscountPercentage;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
public class BookShopApiHelper {
    private static final Logger logger = LoggerFactory.getLogger(BookShopApiHelper.class);

    private final BookRepository bookRepository;

    public BookShopApiHelper(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book mapDTOToBO(AddBookRequest request) {
        Book book = new Book();
        book.setName(request.getName());
        book.setDescription(request.getDescription());
        book.setAuthor(request.getAuthor());
        book.setBookType(request.getBookType());
        book.setPrice(request.getPrice());
        book.setIsbn(request.getIsbn());
        book.setCreatedDate(new Date());
        book.setUpdatedDate(new Date());
        return book;
    }

    public void mapUpdateData(Book books, BooksRequest request) {
        books.setName(isNotBlank(request.getName())?request.getName():books.getName());
        books.setDescription(isNotBlank(request.getDescription())?request.getDescription():books.getDescription());
        books.setAuthor(isNotBlank(request.getAuthor())?request.getAuthor():books.getAuthor());
        books.setBookType(isNotBlank(request.getBookType())?request.getBookType():books.getBookType());
        books.setPrice(request.getPrice()!=null?request.getPrice():books.getPrice());
        books.setIsbn(isNotBlank(request.getIsbn())?request.getIsbn():books.getIsbn());
        books.setUpdatedDate(new Date());
    }

    public CheckoutBooksResult populatePaymentResponse(Map<String, BigDecimal> totalAmountBasedOnClassification, CheckoutBooksRequest request) {
        CheckoutBooksResult result = CheckoutBooksResultConverter.populateCheckoutSuccessfulResponse(request);
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal discountedAmount = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(totalAmountBasedOnClassification)) {
            for(String bookType : totalAmountBasedOnClassification.keySet()) {
                totalAmount = totalAmount.add(totalAmountBasedOnClassification.get(bookType));
                if (BookType.FICTION.getValue().equalsIgnoreCase(bookType) && fictionDiscountPercentage.compareTo(BigDecimal.ZERO)>0) {
                    logger.debug("Applying discount if the bookType is FICTION. fictionDiscountPercentage -{} ", fictionDiscountPercentage);
                    discountedAmount = discountedAmount.add(totalAmountBasedOnClassification.get(bookType))
                            .multiply(fictionDiscountPercentage);
                }
                if (BookType.COMIC.getValue().equalsIgnoreCase(bookType) && comicDiscountPercentage.compareTo(BigDecimal.ZERO)>0) {
                    logger.debug("Applying discount if the bookType is COMIC. fictionDiscountPercentage -{} ", comicDiscountPercentage);
                    discountedAmount = discountedAmount.add(totalAmountBasedOnClassification.get(bookType))
                            .multiply(comicDiscountPercentage);
                }
            }
        }
        result.setTotalPriceBeforeDiscount(totalAmount);
        result.setTotalPriceAfterDiscount(totalAmount.subtract(discountedAmount));
        result.setTotalDiscountAmount(discountedAmount);
        result.setTotalPayableAmount(result.getTotalPriceAfterDiscount());
        result.setStatus(RequestStatus.SUCCESS.toString());
        return result;
    }

    public void validateIfBookExistsWithSameIsbn(AddBookRequest request) throws BusinessException {
        Book book = bookRepository.findByIsbn(request.getIsbn());
        if (book!=null && book.getId()!=null) throw new BusinessException(BookShopErrorCodeMapping.duplicateIsbnRequested());
    }
}

