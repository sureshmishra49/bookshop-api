package com.online.bookshop.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.online.bookshop.dto.*;
import com.online.bookshop.exception.BusinessException;
import com.online.bookshop.helper.BookShopApiHelper;
import com.online.bookshop.repository.BookRepository;
import com.online.bookshop.repository.entities.Book;
import com.online.bookshop.service.BookShopService;
import com.online.bookshop.util.BooksResultConverter;
import com.online.bookshop.util.CheckoutBooksResultConverter;
import com.online.bookshop.validator.BookShopRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service("bookShopService")
public class BookShopServiceImpl implements BookShopService {

    protected static final Logger logger = LoggerFactory.getLogger(BookShopServiceImpl.class);
    private final Gson gsonObj = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    private final BookShopRequestValidator requestValidator;
    private final BookShopApiHelper bookShopApiHelper;
    private final BookRepository bookRepository;

    public BookShopServiceImpl(BookShopRequestValidator requestValidator, BookShopApiHelper bookShopApiHelper, BookRepository bookRepository) {
        this.requestValidator = requestValidator;
        this.bookShopApiHelper = bookShopApiHelper;
        this.bookRepository = bookRepository;
    }

    @Override
    public BooksResult getBooks() {
        BooksResult result = null;
        List<BooksInfo> responseList = new ArrayList<>();
        try {
            bookRepository.findAll().forEach(bookObject -> {
                BooksInfo booksInfo = new BooksInfo()
                        .setId(bookObject.getId())
                        .setName(bookObject.getName())
                        .setDescription(bookObject.getDescription())
                        .setAuthor(bookObject.getAuthor())
                        .setBookType(bookObject.getBookType())
                        .setPrice(bookObject.getPrice())
                        .setIsbn(bookObject.getIsbn());
                responseList.add(booksInfo);
            });
            result = CollectionUtils.isEmpty(responseList) ? BooksResultConverter.populateNoDataFoundResponse() : BooksResultConverter.populateAllBooksSuccessfulResponse(responseList);
        } catch (Exception ex) {
            logger.error("BusinessException thrown in getBooks api. message -{}, detailedMessage -{} ", ex.getMessage(), ex);
            result = (ex instanceof BusinessException) ? BooksResultConverter.populateNoDataFoundResponse() : BooksResultConverter.populateSystemErrorResult();
        }
        return result;
    }

    @Override
    public BooksResult getBookById(Long bookId) {
        BooksResult result = null;
        BooksRequest request = new BooksRequest();
        try {
            request.setBookId(bookId);
            requestValidator.validateBooksByIdApi(request);
            Optional<Book> book = bookRepository.findById(request.getBookId());
            result = (book.isPresent()) ? BooksResultConverter.populateSingleBookSuccessfulResponse(book.get()) :  BooksResultConverter.populateNoDataFoundResponse();
        } catch (Exception ex) {
            logger.error("BusinessException thrown in getBookById api. message -{}, detailedMessage -{} ", ex.getMessage(), ex);
            result = (ex instanceof BusinessException) ? BooksResultConverter.populateBusinessExceptionResponse(((BusinessException) ex).getErrorCode()) : BooksResultConverter.populateSystemErrorResult();
        }
        return result;
    }

    @Override
    public BooksResult addBook(AddBookRequest request) {
        BooksResult result = null;
        try{
            requestValidator.validateAddBookApi(request);
            Book book = bookShopApiHelper.mapDTOToBO(request);
            bookRepository.save(book);
            result = BooksResultConverter.populateBookAdditionSuccessfulResponse(book);
        } catch (Exception ex) {
            logger.error("BusinessException thrown in addBook api. message -{}, detailedMessage -{} ", ex.getMessage(), ex);
            result = (ex instanceof BusinessException) ? BooksResultConverter.populateBookAdditionFailedResponse(request, ((BusinessException) ex).getErrorCode()) : BooksResultConverter.populateSystemErrorResult();
        }
        return result;
    }

    @Override
    public BooksResult deleteBookById(Long bookId) {
        BooksRequest request = new BooksRequest();
        BooksResult result = null;
        request.setBookId(bookId);
        try {
            requestValidator.validateBooksByIdApi(request);
            Optional<Book> book = bookRepository.findById(request.getBookId());
            if (book.isPresent()) {
                bookRepository.deleteById(request.getBookId());
                return BooksResultConverter.populateBookDeletionSuccessfulResponse(book.get());
            }
            result = BooksResultConverter.populateNoDataFoundResponse();
        } catch (Exception ex) {
            logger.error("BusinessException thrown in deleteBookById api. message -{}, detailedMessage -{} ", ex.getMessage(), ex);
            result = ex instanceof BusinessException ? BooksResultConverter.populateBusinessExceptionResponse(((BusinessException) ex).getErrorCode()) : BooksResultConverter.populateSystemErrorResult();
        }
        return result;
    }

    @Override
    public BooksResult updateBookByIdAndIsbn(BooksRequest request) {
        BooksResult result = null;
        try {
            requestValidator.validateUpdateBookApi(request);
            List<Book> book = bookRepository.findByIdAndIsbn(request.getBookId(), request.getIsbn());
            if (!CollectionUtils.isEmpty(book)) {
                bookShopApiHelper.mapUpdateData(book.get(0), request);
                bookRepository.save(book.get(0));
                return BooksResultConverter.populateBookAdditionSuccessfulResponse(book.get(0));
            }
            result = BooksResultConverter.populateNoDataFoundResponse();
        } catch (Exception ex) {
            logger.error("BusinessException thrown in updateBookByIdAndIsbn api. message -{}, detailedMessage -{} ", ex.getMessage(), ex);
            result = (ex instanceof BusinessException) ? BooksResultConverter.populateBusinessExceptionResponse(((BusinessException) ex).getErrorCode()) : BooksResultConverter.populateSystemErrorResult();
        }
        return result;
    }

    @Override
    @Transactional
    public CheckoutBooksResult processCheckout(CheckoutBooksRequest request) {
        CheckoutBooksResult result = null;;
        try {
            requestValidator.validateCheckoutBooksApi(request);
            Map<String, BigDecimal> totalAmountBasedOnClassification = new HashMap<>();
            Map<String, Long> counting = request.getBooksList().stream().collect(
                    Collectors.groupingBy(CheckoutRequest::getBookType, Collectors.counting()));
            totalAmountBasedOnClassification = request.getBooksList().stream().collect(
                    Collectors.groupingBy(CheckoutRequest::getBookType, Collectors.mapping(CheckoutRequest::getPrice, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

            logger.debug("totalAmountBasedOnClassification --> {} ", totalAmountBasedOnClassification);
            result = bookShopApiHelper.populatePaymentResponse(totalAmountBasedOnClassification, request);
        } catch (Exception ex) {
            logger.error("BusinessException thrown. message -{}, detailedMessage -{} ", ex.getMessage(), ex);
            result = (ex instanceof BusinessException) ? CheckoutBooksResultConverter.populateCheckoutBooksBusinessExceptionResult(request, ((BusinessException) ex).getErrorCode()) :  CheckoutBooksResultConverter.populateSystemErrorInCheckOutApiResult();
        }
        return result;
    }
}
