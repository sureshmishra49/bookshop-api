package com.online.bookshop.exception;

public class BookShopErrorCodeMapping {
    public static ErrorCode bookIdRequired() {  return new ErrorCode("BOOK_ID_REQUIRED", "Book Id is required"); }
    public static ErrorCode bookAdditionInputRequired() {  return new ErrorCode("INPUT_REQUIRED", "Book addition input is required"); }
    public static ErrorCode bookNameRequired() {  return new ErrorCode("BOOK_NAME_REQUIRED", "Book Name is required"); }
    public static ErrorCode bookAuthorRequired() {  return new ErrorCode("BOOK_AUTHOR_REQUIRED", "Book Author is required"); }
    public static ErrorCode bookDescriptionRequired() {  return new ErrorCode("BOOK_DESCRIPTION_REQUIRED", "Book Description is required"); }
    public static ErrorCode bookTypeRequired() {  return new ErrorCode("BOOK_TYPE_REQUIRED", "Book Type is required"); }
    public static ErrorCode bookPriceRequired() {  return new ErrorCode("BOOK_PRICE_REQUIRED", "Book Price is required"); }
    public static ErrorCode bookPriceCannotBeNegative() {  return new ErrorCode("NEGATIVE_BOOK_PRICE_NOT_ALLOWED", "Book Price cannot be in negative required"); }
    public static ErrorCode bookISBNRequired() {  return new ErrorCode("BOOK_ISBN_REQUIRED", "Book ISBN is required"); }
    public static ErrorCode systemError() { return new ErrorCode("SYSTEM_ERROR", "System error"); }

    public static ErrorCode inputRequired() {  return new ErrorCode("INPUT_REQUIRED", "Request input is required"); }
    public static ErrorCode bookListIsEmpty() {  return new ErrorCode("BOOK_LIST_REQUIRED", "Book list is required"); }
    public static ErrorCode duplicateIsbnRequested() {  return new ErrorCode("DUPLICATE_ISBN_REQUESTED", "Book with same ISBN already exists"); }


}