package com.online.bookshop;

import com.online.bookshop.controller.BookShopController;
import com.online.bookshop.dto.*;
import com.online.bookshop.repository.BookRepository;
import com.online.bookshop.repository.entities.Book;
import com.online.bookshop.service.BookShopService;
import com.online.bookshop.util.Utils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.online.bookshop.constants.RequestStatus.SUCCESS;
import static com.online.bookshop.controller.test.helper.AddBookApiTestMocks.*;
import static com.online.bookshop.controller.test.helper.GetBooksApiTestMocks.mockResponse_NoRecordFoundInDB;
import static com.online.bookshop.controller.test.helper.UpdateBookApiTestMocks.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookShopApplicationTests {
	@Mock
	BookShopService bookShopService;
	@Mock
	BookRepository bookRepository;
	@Mock
	Utils utils;
	@InjectMocks
	BookShopController controller;



	/*
		addBook API Test cases - starts here
	 */
	@Test
	public void addBookSuccessfulWithAllAttribute() {
		AddBookRequest request = mockAddBookRequest();
		when(bookShopService.addBook(request)).thenReturn(mockAddBookResponseResponse(request));
		when(bookRepository.save(mockBookObject(request))).thenReturn(mockBookObject(request));
		ResponseEntity<?> response = controller.createBooks(request);
		Assert.assertNotNull(response.getStatusCode().value());
		BooksResult result = (BooksResult) response.getBody();
		Assert.assertEquals(result.getBookInfo().size(), 1);
		Assert.assertEquals(result.getBookInfo().get(0).getIsbn(), "12345654321");
	}

	@Test
	public void addBookWithOutAnyInput() {
		AddBookRequest request = mockAddBookRequest();
		request.setIsbn(EMPTY);
		when(bookShopService.addBook(request)).thenReturn(mockResponse_AddBookWithOutAnyInput(request));
		ResponseEntity<?> response = controller.createBooks(request);
		Assert.assertNotNull(response.getStatusCode().value());
		BooksResult result = (BooksResult) response.getBody();
		Assert.assertEquals(result.getStatus(), "FAILURE");
		Assert.assertEquals(result.getErrorMessage().getCode(), "INPUT_REQUIRED");
	}
	@Test
	public void addBookWithOutBookName() {
		AddBookRequest request = mockAddBookRequest();
		request.setIsbn(EMPTY);
		when(bookShopService.addBook(request)).thenReturn(mockResponse_AddBookWithOutBookName(request));
		ResponseEntity<?> response = controller.createBooks(request);
		Assert.assertNotNull(response.getStatusCode().value());
		BooksResult result = (BooksResult) response.getBody();
		Assert.assertEquals(result.getStatus(), "FAILURE");
		Assert.assertEquals(result.getErrorMessage().getCode(), "BOOK_NAME_REQUIRED");
	}
	@Test
	public void addBookWithOutBookDescription() {
		AddBookRequest request = mockAddBookRequest();
		request.setIsbn(EMPTY);
		when(bookShopService.addBook(request)).thenReturn(mockResponse_AddBookWithOutBookDescription(request));
		ResponseEntity<?> response = controller.createBooks(request);
		Assert.assertNotNull(response.getStatusCode().value());
		BooksResult result = (BooksResult) response.getBody();
		Assert.assertEquals(result.getStatus(), "FAILURE");
		Assert.assertEquals(result.getErrorMessage().getCode(), "BOOK_DESCRIPTION_REQUIRED");
	}
	@Test
	public void addBookWithOutBookISBN() {
		AddBookRequest request = mockAddBookRequest();
		request.setIsbn(EMPTY);
		when(bookShopService.addBook(request)).thenReturn(mockResponse_AddBookWithOutISBN(request));
		ResponseEntity<?> response = controller.createBooks(request);
		Assert.assertNotNull(response.getStatusCode().value());
		BooksResult result = (BooksResult) response.getBody();
		Assert.assertEquals(result.getStatus(), "FAILURE");
		Assert.assertEquals(result.getErrorMessage().getCode(), "BOOK_ISBN_REQUIRED");
	}
	@Test
	public void addBookWithOutBookAuthor() {
		AddBookRequest request = mockAddBookRequest();
		request.setIsbn(EMPTY);
		when(bookShopService.addBook(request)).thenReturn(mockResponse_AddBookWithOutBookAuthor(request));
		ResponseEntity<?> response = controller.createBooks(request);
		Assert.assertNotNull(response.getStatusCode().value());
		BooksResult result = (BooksResult) response.getBody();
		Assert.assertEquals(result.getStatus(), "FAILURE");
		Assert.assertEquals(result.getErrorMessage().getCode(), "BOOK_AUTHOR_REQUIRED");
	}
	@Test
	public void addBookWithOutBookType() {
		AddBookRequest request = mockAddBookRequest();
		request.setIsbn(EMPTY);
		when(bookShopService.addBook(request)).thenReturn(mockResponse_AddBookWithOutBookType(request));
		ResponseEntity<?> response = controller.createBooks(request);
		Assert.assertNotNull(response.getStatusCode().value());
		BooksResult result = (BooksResult) response.getBody();
		Assert.assertEquals(result.getStatus(), "FAILURE");
		Assert.assertEquals(result.getErrorMessage().getCode(), "BOOK_TYPE_REQUIRED");
	}
	@Test
	public void addBookWithOutBookPrice() {
		AddBookRequest request = mockAddBookRequest();
		request.setIsbn(EMPTY);
		when(bookShopService.addBook(request)).thenReturn(mockResponse_AddBookWithOutBookPrice(request));
		ResponseEntity<?> response = controller.createBooks(request);
		Assert.assertNotNull(response.getStatusCode().value());
		BooksResult result = (BooksResult) response.getBody();
		Assert.assertEquals(result.getStatus(), "FAILURE");
		Assert.assertEquals(result.getErrorMessage().getCode(), "BOOK_PRICE_REQUIRED");
	}
	/*
    	addBook API Test cases - ends here
    */

	/*
    	getAllBooks API Test cases - starts here
    */
	@Test
	public void getAllBooks() {
		when(bookShopService.getBooks()).thenReturn(mockResponse(mockRequest()));
		ResponseEntity<?> response = controller.getBooks();
		Assert.assertNotNull(response.getStatusCode().value());
		BooksResult result = (BooksResult) response.getBody();
		Assert.assertEquals(result.getBookInfo().size(), 1);
		Assert.assertEquals(result.getBookInfo().get(0).getIsbn(), "12345654321");
	}
	@Test
	public void getAllBooksNoRecordsFoundInDB() {
		when(bookShopService.getBooks()).thenReturn(mockResponse_NoRecordFoundInDB());
		ResponseEntity<?> response = controller.getBooks();
		Assert.assertNotNull(response.getStatusCode().value());
		BooksResult result = (BooksResult) response.getBody();
		Assert.assertEquals(result.getBookInfo().size(), 0);
		Assert.assertEquals(result.getStatus(), "NO_DATA_FOUND");
	}
	/*
    	getAllBooks API Test cases - ends here
    */

	/*
    	getBookById API Test cases - starts here
    */
	@Test
	public void getBookByIdWithSuccessfulResponse() {
		BooksRequest request = mockRequest();
		request.setBookId(1l);
		when(bookShopService.getBookById(request.getBookId())).thenReturn(mockResponse(request));
		ResponseEntity<?> response = controller.getBookById(request.getBookId());
		Assert.assertNotNull(response.getStatusCode().value());
		BooksResult result = (BooksResult) response.getBody();
		Assert.assertEquals(result.getBookInfo().size(), 1);
		Assert.assertEquals(result.getBookInfo().get(0).getIsbn(), "12345654321");
	}
	@Test
	public void getBookByIdNoRecordsFoundInDB() {
		BooksRequest request = mockRequest();
		request.setBookId(2l);
		when(bookShopService.getBookById(request.getBookId())).thenReturn(mockResponse_NoRecordFoundInDB());
		ResponseEntity<?> response = controller.getBookById(request.getBookId());
		Assert.assertNotNull(response.getStatusCode().value());
		BooksResult result = (BooksResult) response.getBody();
		Assert.assertEquals(result.getBookInfo().size(), 0);
		Assert.assertEquals(result.getStatus(), "NO_DATA_FOUND");
	}
	/*
    	getAllBooks ApI Test cases - ends here
    */

	/*
    	deleteBookById API Test cases - starts here
    */
	@Test
	public void deleteBookByIdWithSuccessfulDeletion() {
		BooksRequest request = mockRequest();
		request.setBookId(1l);
		when(bookShopService.deleteBookById(request.getBookId())).thenReturn(mockResponse(request));
		ResponseEntity<?> response = controller.deleteBookById(request.getBookId());
		Assert.assertNotNull(response.getStatusCode().value());
		BooksResult result = (BooksResult) response.getBody();
		Assert.assertEquals(result.getBookInfo().size(), 1);
		Assert.assertEquals(result.getBookInfo().get(0).getIsbn(), "12345654321");
	}
	@Test
	public void deleteBookByIdWithNoRecordsFoundInDB() {
		BooksRequest request = mockRequest();
		request.setBookId(2l);
		when(bookShopService.deleteBookById(request.getBookId())).thenReturn(mockResponse_NoRecordFoundInDB());
		ResponseEntity<?> response = controller.deleteBookById(request.getBookId());
		Assert.assertNotNull(response.getStatusCode().value());
		BooksResult result = (BooksResult) response.getBody();
		Assert.assertEquals(result.getBookInfo().size(), 0);
		Assert.assertEquals(result.getStatus(), "NO_DATA_FOUND");
	}
	/*
    	deleteBookById ApI Test cases - ends here
    */

	/*
    	updateBook API Test cases - starts here
    */
	@Test
	public void updateBookWithSuccessfulResponse() {
		BooksRequest request = mockRequest();
		request.setBookId(1l);
		when(bookShopService.updateBookByIdAndIsbn(request)).thenReturn(mockResponse(request));
		ResponseEntity<?> response = controller.updateBook(request);
		Assert.assertNotNull(response.getStatusCode().value());
		BooksResult result = (BooksResult) response.getBody();
		Assert.assertEquals(result.getBookInfo().size(), 1);
		Assert.assertEquals(result.getBookInfo().get(0).getIsbn(), "12345654321");
	}
	@Test
	public void updateBookWithSystemErrorResponse() {
		BooksRequest request = mockRequest();
		request.setBookId(2l);
		when(bookShopService.updateBookByIdAndIsbn(request)).thenReturn(mockResponse_UpdateBookSystemErrorResponse(request));
		ResponseEntity<?> response = controller.updateBook(request);
		Assert.assertNotNull(response.getStatusCode().value());
		BooksResult result = (BooksResult) response.getBody();
		Assert.assertEquals(result.getBookInfo().size(), 1);
		Assert.assertEquals(result.getStatus(), "FAILURE");
		Assert.assertEquals(result.getErrorMessage().getCode(), "SYSTEM_ERROR");
	}
	@Test
	public void updateBookWithOutAnyInput() {
		BooksRequest request = mockRequest();
		request.setBookId(2l);
		when(bookShopService.updateBookByIdAndIsbn(request)).thenReturn(mockResponse_UpdateBookWithOutAnyInput(request));
		ResponseEntity<?> response = controller.updateBook(request);
		Assert.assertNotNull(response.getStatusCode().value());
		BooksResult result = (BooksResult) response.getBody();
		Assert.assertEquals(result.getBookInfo().size(), 1);
		Assert.assertEquals(result.getStatus(), "FAILURE");
		Assert.assertEquals(result.getErrorMessage().getCode(), "INPUT_REQUIRED");
	}
	@Test
	public void updateBookWithOutISBN() {
		BooksRequest request = mockRequest();
		request.setBookId(2l);
		when(bookShopService.updateBookByIdAndIsbn(request)).thenReturn(mockResponse_UpdateBookWithOutISBN(request));
		ResponseEntity<?> response = controller.updateBook(request);
		Assert.assertNotNull(response.getStatusCode().value());
		BooksResult result = (BooksResult) response.getBody();
		Assert.assertEquals(result.getBookInfo().size(), 1);
		Assert.assertEquals(result.getStatus(), "FAILURE");
		Assert.assertEquals(result.getErrorMessage().getCode(), "BOOK_ISBN_REQUIRED");
	}
	@Test
	public void updateBookWithOutBookId() {
		BooksRequest request = mockRequest();
		request.setBookId(2l);
		when(bookShopService.updateBookByIdAndIsbn(request)).thenReturn(mockResponse_UpdateBookWithOutBookId(request));
		ResponseEntity<?> response = controller.updateBook(request);
		Assert.assertNotNull(response.getStatusCode().value());
		BooksResult result = (BooksResult) response.getBody();
		Assert.assertEquals(result.getBookInfo().size(), 1);
		Assert.assertEquals(result.getStatus(), "FAILURE");
		Assert.assertEquals(result.getErrorMessage().getCode(), "BOOK_ID_REQUIRED");
	}
	/*
    	updateBook API Test cases - ends here
    */

	/*
            checkoutBooks API Test cases - starts here
        */
	@Test
	public void checkoutSingleBookOrderWithSuccessfulResponse() {
		CheckoutBooksRequest request = mockCheckoutBooksRequestForSingleBooksOrder();
		when(bookShopService.processCheckout(request)).thenReturn(mockCheckoutBooksResponseForSingleBooksOrder(request));
		ResponseEntity<?> response = controller.checkoutBooks(request);
		Assert.assertNotNull(response.getStatusCode().value());
		CheckoutBooksResult result = (CheckoutBooksResult) response.getBody();
		Assert.assertEquals(result.getBooksInfo().size(), 1);
		Assert.assertEquals(result.getTotalPriceBeforeDiscount(), new BigDecimal(100));
		Assert.assertEquals(result.getTotalPriceAfterDiscount(), new BigDecimal(90));
		Assert.assertEquals(result.getTotalDiscountAmount(), new BigDecimal(10));
		Assert.assertEquals(result.getTotalPayableAmount(), new BigDecimal(90));
		Assert.assertEquals(result.getStatus(), "SUCCESS");
	}
	@Test
	public void checkoutMoreThanOneBookOrderWithSuccessfulResponse() {
		CheckoutBooksRequest request = mockCheckoutBooksRequestForMoreThanOneBooksOrder();
		when(bookShopService.processCheckout(request)).thenReturn(mockCheckoutBooksResponseForMoreThanOneBooksOrder(request));
		ResponseEntity<?> response = controller.checkoutBooks(request);
		Assert.assertNotNull(response.getStatusCode().value());
		CheckoutBooksResult result = (CheckoutBooksResult) response.getBody();
		Assert.assertEquals(result.getBooksInfo().size(), 2);
		Assert.assertEquals(result.getTotalPriceBeforeDiscount(), new BigDecimal(400));
		Assert.assertEquals(result.getTotalPriceAfterDiscount(), new BigDecimal(390));
		Assert.assertEquals(result.getTotalDiscountAmount(), new BigDecimal(10));
		Assert.assertEquals(result.getTotalPayableAmount(), new BigDecimal(390));
		Assert.assertEquals(result.getStatus(), "SUCCESS");
	}
	/*
    	checkoutBooks API Test cases - ends here
    */

	private BooksRequest mockRequest() {
		BooksRequest request = new BooksRequest();
		request.setName("THE MONK WHO SOLD HIS FERRARI");
		request.setDescription("ABOUT MONK");
		request.setBookType("FICTION");
		request.setAuthor("ROBIN SHARMA");
		request.setPrice(new BigDecimal(100));
		request.setIsbn("12345654321");
		return request;
	}

	private AddBookRequest mockAddBookRequest() {
		AddBookRequest request = new AddBookRequest();
		request.setName("THE MONK WHO SOLD HIS FERRARI");
		request.setDescription("ABOUT MONK");
		request.setBookType("FICTION");
		request.setAuthor("ROBIN SHARMA");
		request.setPrice(new BigDecimal(100));
		request.setIsbn("12345654321");
		return request;
	}

	private BooksResult mockResponse(BooksRequest request) {
		List<BooksInfo> responseList = new ArrayList<>();
		BooksInfo booksInfo = new BooksInfo()
				.setName(request.getName())
				.setDescription(request.getDescription())
				.setAuthor(request.getAuthor())
				.setBookType(request.getBookType())
				.setPrice(request.getPrice())
				.setIsbn(request.getIsbn());
		responseList.add(booksInfo);
		return BooksResult.builder()
				.bookInfo(responseList)
				.status(SUCCESS.toString())
				.build();
	}

	private BooksResult mockAddBookResponseResponse(AddBookRequest request) {
		List<BooksInfo> responseList = new ArrayList<>();
		BooksInfo booksInfo = new BooksInfo()
				.setName(request.getName())
				.setDescription(request.getDescription())
				.setAuthor(request.getAuthor())
				.setBookType(request.getBookType())
				.setPrice(request.getPrice())
				.setIsbn(request.getIsbn());
		responseList.add(booksInfo);
		return BooksResult.builder()
				.bookInfo(responseList)
				.status(SUCCESS.toString())
				.build();
	}

	private CheckoutBooksRequest mockCheckoutBooksRequestForSingleBooksOrder() {
		CheckoutBooksRequest checkoutBooksRequest = new CheckoutBooksRequest();
		List<CheckoutRequest> booksList = new ArrayList<>();
		CheckoutRequest request = new CheckoutRequest();
		request.setName("THE MONK WHO SOLD HIS FERRARI");
		request.setDescription("ABOUT MONK");
		request.setBookType("FICTION");
		request.setAuthor("ROBIN SHARMA");
		request.setPrice(new BigDecimal(100));
		request.setIsbn("12345654321");
		booksList.add(request);
		checkoutBooksRequest.setBooksList(booksList);
		return checkoutBooksRequest;
	}

	private CheckoutBooksResult mockCheckoutBooksResponseForSingleBooksOrder(CheckoutBooksRequest request) {
		CheckoutRequest booksRequest = request.getBooksList().get(0);
		List<BooksInfo> responseList = new ArrayList<>();
		BooksInfo booksInfo = new BooksInfo()
				.setId(booksRequest.getBookId())
				.setName(booksRequest.getName())
				.setDescription(booksRequest.getDescription())
				.setAuthor(booksRequest.getAuthor())
				.setBookType(booksRequest.getBookType())
				.setPrice(booksRequest.getPrice())
				.setIsbn(booksRequest.getIsbn());
		responseList.add(booksInfo);
		return CheckoutBooksResult.builder()
				.booksInfo(responseList)
				.totalPriceBeforeDiscount(new BigDecimal(100))
				.totalPriceAfterDiscount(new BigDecimal(90))
				.totalDiscountAmount(new BigDecimal(10))
				.totalPayableAmount(new BigDecimal(90))
				.status(SUCCESS.toString())
				.build();
	}

	private CheckoutBooksRequest mockCheckoutBooksRequestForMoreThanOneBooksOrder() {
		CheckoutBooksRequest checkoutBooksRequest = new CheckoutBooksRequest();
		List<CheckoutRequest> booksList = new ArrayList<>();

		CheckoutRequest request = new CheckoutRequest();
		request.setName("THE MONK WHO SOLD HIS FERRARI");
		request.setDescription("ABOUT MONK");
		request.setBookType("FICTION");
		request.setAuthor("ROBIN SHARMA");
		request.setPrice(new BigDecimal(100));
		request.setIsbn("12345654321");
		booksList.add(request);

		CheckoutRequest secondReq = new CheckoutRequest();
		secondReq.setName("ONE PLUS ONE IS ELEVAN");
		secondReq.setDescription("Drama book");
		secondReq.setBookType("COMIC");
		secondReq.setAuthor("Mr Bean");
		secondReq.setPrice(new BigDecimal(200));
		secondReq.setIsbn("4321");
		booksList.add(secondReq);

		checkoutBooksRequest.setBooksList(booksList);
		return checkoutBooksRequest;
	}

	private CheckoutBooksResult mockCheckoutBooksResponseForMoreThanOneBooksOrder(CheckoutBooksRequest request) {
		List<BooksInfo> responseList = new ArrayList<>();
		request.getBooksList().stream().forEach(bookInfo -> {
			BooksInfo booksInfo = new BooksInfo()
					.setId(bookInfo.getBookId())
					.setName(bookInfo.getName())
					.setDescription(bookInfo.getDescription())
					.setAuthor(bookInfo.getAuthor())
					.setBookType(bookInfo.getBookType())
					.setPrice(bookInfo.getPrice())
					.setIsbn(bookInfo.getIsbn());
			responseList.add(booksInfo);
		});
		return CheckoutBooksResult.builder()
				.booksInfo(responseList)
				.totalPriceBeforeDiscount(new BigDecimal(400))
				.totalPriceAfterDiscount(new BigDecimal(390))
				.totalDiscountAmount(new BigDecimal(10))
				.totalPayableAmount(new BigDecimal(390))
				.status(SUCCESS.toString())
				.build();
	}
	public Book mockBookObject(AddBookRequest request) {
		Book book = new Book();
		book.setId(1l);
		book.setName(request.getName());
		book.setDescription(request.getDescription());
		book.setAuthor(request.getAuthor());
		book.setBookType(request.getBookType());
		book.setPrice(request.getPrice());
		book.setIsbn(request.getIsbn());
		return book;
	}
}
