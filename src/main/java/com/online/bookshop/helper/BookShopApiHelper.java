package com.online.bookshop.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.online.bookshop.constants.BookType;
import com.online.bookshop.constants.RequestStatus;
import com.online.bookshop.dto.AddBookRequest;
import com.online.bookshop.dto.BooksRequest;
import com.online.bookshop.dto.CheckoutBooksRequest;
import com.online.bookshop.dto.CheckoutBooksResult;
import com.online.bookshop.repository.entities.Book;
import com.online.bookshop.util.CheckoutBooksResultConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Map;

import static com.online.bookshop.constants.AppConstant.*;
import static com.online.bookshop.constants.AppConstant.comicDiscountPercentage;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
public class BookShopApiHelper {

    private static final Logger logger = LoggerFactory.getLogger(BookShopApiHelper.class);
    private final Gson gsonObj = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    public Book mapDTOToBO(AddBookRequest request) {
        Book book = new Book();
        book.setName(request.getName());
        book.setDescription(request.getDescription());
        book.setAuthor(request.getAuthor());
        book.setBookType(request.getBookType());
        book.setPrice(request.getPrice());
        book.setIsbn(request.getIsbn());
        return book;
    }

    public void mapUpdateData(Book books, BooksRequest request) {
        books.setName(isNotBlank(request.getName())?request.getName():books.getName());
        books.setDescription(isNotBlank(request.getDescription())?request.getDescription():books.getDescription());
        books.setAuthor(isNotBlank(request.getAuthor())?request.getAuthor():books.getAuthor());
        books.setBookType(isNotBlank(request.getBookType())?request.getBookType():books.getBookType());
        books.setPrice(request.getPrice()!=null?request.getPrice():books.getPrice());
        books.setIsbn(isNotBlank(request.getIsbn())?request.getIsbn():books.getIsbn());
    }

    public CheckoutBooksResult populatePaymentResponse(Map<String, BigDecimal> totalAmountBasedOnClassification, CheckoutBooksRequest request) {
        CheckoutBooksResult result = CheckoutBooksResultConverter.populateCheckoutSuccessfulResponse(request);
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal discountedAmount = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(totalAmountBasedOnClassification)) {
            for(String bookType : totalAmountBasedOnClassification.keySet()) {
                totalAmount = totalAmount.add(totalAmountBasedOnClassification.get(bookType));
                if (BookType.FICTION.getValue().equalsIgnoreCase(bookType) && fictionDiscountPercentage.compareTo(BigDecimal.ZERO)>0) {
                    discountedAmount = discountedAmount.add(totalAmountBasedOnClassification.get(bookType))
                            .multiply(fictionDiscountPercentage);
                }
                if (BookType.COMIC.getValue().equalsIgnoreCase(bookType) && comicDiscountPercentage.compareTo(BigDecimal.ZERO)>0) {
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
}

