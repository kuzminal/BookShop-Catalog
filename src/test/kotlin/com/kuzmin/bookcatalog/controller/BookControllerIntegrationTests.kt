package com.kuzmin.bookcatalog.controller

import com.kuzmin.bookcatalog.model.entity.Book
import com.kuzmin.bookcatalog.service.BookService
import org.assertj.core.api.Assertions.assertThat
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BookControllerIntegrationTests(val bookService: BookService) {
    val testRestTemplate: TestRestTemplate = TestRestTemplate()

    @Test
    fun whenPostRequestThenBookCreated() {
        val expectedBook = Book(ObjectId.get(), "9783161484101", "Title", "Author", "1991", 9.90)
        val response: ResponseEntity<Book> = testRestTemplate.postForEntity(
            "http://localhost:9001/books", expectedBook,
            Book::class.java
        )
        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        assertThat(response.body).isNotNull
        assertThat(response.body?.isbn.orEmpty()).isEqualTo(expectedBook.isbn)
        bookService.deleteByIsbn(expectedBook.isbn)
    }
}