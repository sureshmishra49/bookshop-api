package com.online.bookshop.service;

import com.online.bookshop.dto.*;

public interface BookShopService {
    public BooksResult getBooks();
    BooksResult getBookById(Long bookId);
    BooksResult addBook(AddBookRequest model);
    BooksResult deleteBookById(Long bookId);
    BooksResult updateBookByIdAndIsbn(BooksRequest model);
    CheckoutBooksResult processCheckout(CheckoutBooksRequest request);
}
