# Online Bookshop Rest API
This project is created to achieve the following:

Develop Rest API’s for – 
- CRUD operations on Books. 
- Checkout operation for single or multiple books which will return the total payable amount.
	
Considerations: 
- You can use either Spring Boot or Quarkus 
- Book object should have the following attributes: 
	- Name 
	- Description 
	- Author 
	- Type/Classification 
	- Price ISBN 
- Checkout method will take list of books as parameters plus optional promotion code and return total price after discount (if applicable). 
- Promotion/Discounts is variant according to book type/classification, ex: fiction books may have 10% discount while comic books have 0% discount.	

Please follow the following steps for running this application:

**System Requirement**

	-Maven

	-Java 8

**Local Setup**

	-Download the project from GIT

	-Once it is available in your system, import the project as maven project in your working IDE(example Intellij)

	-Run Clean install from 
		- intellij using - Maven > Lifecycle > clean
		-Or using the following command prompt
			-Go to the directory of your downloaded project which contains pom.xml and run the following command

			mvn clean install
	-Once the build is successful, right click on the following file to run the spring boot application
			BookShopApplication.java

**Flow of API calls**

	1-addBook API - http://localhost:8080/bookshop-api/v1/books (POST) - To add books
	2-getBookById API - http://localhost:8080/bookshop-api/v1/books/1 (GET) - Retreive the book by its Id
	3-getBooks API - http://localhost:8080/bookshop-api/v1/books (GET) - Retreive all the books available
	4-deleteBookById API - http://localhost:8080/bookshop-api/v1/books/1 (DELETE) - Delete any book if not required
	5-updateBook API - http://localhost:8080/bookshop-api/v1/books (PUT) - Update the details of any book
	6-checkOutBooks API - http://localhost:8080/bookshop-api/v1/checkout (POST) - Used for checkout the selected book/books

**Postman Collection**

	-Postman collection can be found in following location:
		folder Name: resource 
		fileName: bookShopApiCollection
**Unit Test**

	-Unit test file is found in test folder for the application in the following folder
		com.online.bookshop
		Right-click on BookShopApplicationTests and selct Run
	-The above should run the unit test cases for the project

**Project URLs**

	-Swagger Documentation: <base_url>/bookshop-api/swagger-ui.html
		Example: http://localhost:8080/bookshop-api/swagger-ui.html

	-JSON Api-Docs: <base_url>/bookshop-api/v2/api-docs
		Example: http://localhost:8080/bookshop-api/v2/api-docs

	-H2DB Console: <base_url>/bookshop-api/h2-ui
		Example: http://localhost:8080/bookshop-api/h2-ui

		<base_url>: http://localhost:8080
		
**Run in Docker**
	-Open the Terminal in your IDE and go to the folder that contains Dockerfile in the project
	-Run the following command
		-docker build --platform linux/amd64 -t  online-bookshop .
		-docker run -p 8080:8080 -t online-bookshop
	-Call the following URL to check the health of the BookShop application
		http://localhost:8080/bookshop-api/v1/books/health
	-The app is up and running fine if you see the following output
		BookShop API is up and running
	-Start calling the other APIs
