package com.online.bookshop.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.online.bookshop.dto.*;
import com.online.bookshop.helper.BookShopApiHelper;
import com.online.bookshop.service.BookShopService;
import com.online.bookshop.util.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/books")
@Api(tags = "BookShop APIs")
public class BookShopController {
    private static final Logger logger = LoggerFactory.getLogger(BookShopController.class);
    private final Gson gsonObj = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    private final BookShopService bookShopService;
    private final Utils utils;
    private final BookShopApiHelper bookShopApiHelper;

    public BookShopController(BookShopService bookShopService, Utils utils, BookShopApiHelper bookShopApiHelper) {
        this.bookShopService = bookShopService;
        this.utils = utils;
        this.bookShopApiHelper = bookShopApiHelper;
    }

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public String bookApiHealthCheck() {
        return "BookShop API is up and running";
    }

    @ApiOperation(value = "Get All Books")
    @GetMapping
    public ResponseEntity<BooksResult> getBooks() {
        BooksResult result = bookShopService.getBooks();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get a single book based on id")
    @GetMapping(value = "/{bookId}")
    public ResponseEntity<BooksResult> getBookById(@PathVariable("bookId") Long bookId) {
        BooksResult result = bookShopService.getBookById(bookId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Add new books")
    @PostMapping
    public ResponseEntity<BooksResult> createBooks(@RequestBody AddBookRequest request) {
        utils.logRequestAndResponse("createBooks", request, null);
        BooksResult result = bookShopService.addBook(request);
        utils.logRequestAndResponse("createBooks", request, result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a book for the given bookId")
    @DeleteMapping(value = "/{bookId}")
    public ResponseEntity<BooksResult> deleteBookById(@PathVariable("bookId") Long bookId) {
        BooksResult result = bookShopService.deleteBookById(bookId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Update the details for the given bookId")
    @PutMapping
    public ResponseEntity<BooksResult> updateBook(@RequestBody BooksRequest request) {
        BooksResult result = bookShopService.updateBookByIdAndIsbn(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Checkout selected books for payment")
    @PostMapping(value = "/checkout")
    public ResponseEntity<CheckoutBooksResult> checkoutBooks(@RequestBody CheckoutBooksRequest request) {
        utils.logRequestAndResponse("checkoutBooks", request, null);
        CheckoutBooksResult result = bookShopService.processCheckout(request);
        utils.logRequestAndResponse("checkoutBooks", request, result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
