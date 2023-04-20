package com.online.bookshop.service.impl;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service("bookShopService")
public class BookShopServiceImpl implements BookShopService {
    protected static final Logger logger = LoggerFactory.getLogger(BookShopServiceImpl.class);

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
        BooksResult result;
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
                        .setIsbn(bookObject.getIsbn())
                        .setCreatedDate(bookObject.getCreatedDate()!=null?bookObject.getCreatedDate().toString():EMPTY);
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
        BooksResult result;
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
        BooksResult result;
        try{
            bookShopApiHelper.validateIfBookExistsWithSameIsbn(request);
            requestValidator.validateAddBookApi(request);
            logger.debug("Validate AddBookApi. bookName -{}, bookType -{} ", request.getName(), request.getBookType());
            Book book = bookShopApiHelper.mapDTOToBO(request);
            bookRepository.save(book);
            logger.debug("Book added. bookId -{}, bookName -{}, bookType -{} ", book.getId(), request.getName(), request.getBookType());
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
        BooksResult result;
        request.setBookId(bookId);
        try {
            requestValidator.validateBooksByIdApi(request);
            logger.debug("Validate DeleteBookById. bookId -{} ", request.getBookId());
            Optional<Book> book = bookRepository.findById(request.getBookId());
            if (book.isPresent()) {
                logger.debug("Book is going to be deleted. bookId -{}, bookName -{}, bookType -{} ", book.get().getId(), book.get().getName(), book.get().getBookType());
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
        BooksResult result;
        try {
            requestValidator.validateUpdateBookApi(request);
            logger.debug("Validate updateBookByIdAndIsbn. bookId -{}, isbn -{} ", request.getBookId(), request.getIsbn());
            List<Book> book = bookRepository.findByIdAndIsbn(request.getBookId(), request.getIsbn());
            if (!CollectionUtils.isEmpty(book)) {
                bookShopApiHelper.mapUpdateData(book.get(0), request);
                bookRepository.save(book.get(0));
                logger.debug("Book is going to be updated. bookId -{}, bookName -{}, bookType -{} ", book.get(0).getId(), book.get(0).getName(), book.get(0).getBookType());
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
        CheckoutBooksResult result;
        try {
            requestValidator.validateCheckoutBooksApi(request);
            logger.debug("Validate processCheckout. bookList size -{} ", request.getBooksList().size());
            Map<String, BigDecimal> totalAmountBasedOnClassification;
            totalAmountBasedOnClassification = request.getBooksList().stream().collect(
                    Collectors.groupingBy(CheckoutRequest::getBookType, Collectors.mapping(CheckoutRequest::getPrice, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

            logger.debug("totalAmountBasedOnClassification -{} ", totalAmountBasedOnClassification);
            result = bookShopApiHelper.populatePaymentResponse(totalAmountBasedOnClassification, request);
        } catch (Exception ex) {
            logger.error("BusinessException thrown. message -{}, detailedMessage -{} ", ex.getMessage(), ex);
            result = (ex instanceof BusinessException) ? CheckoutBooksResultConverter.populateCheckoutBooksBusinessExceptionResult(((BusinessException) ex).getErrorCode()) :  CheckoutBooksResultConverter.populateSystemErrorInCheckOutApiResult();
        }
        return result;
    }
}
