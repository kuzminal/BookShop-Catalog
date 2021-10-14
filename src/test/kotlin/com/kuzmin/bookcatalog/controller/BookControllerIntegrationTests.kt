package com.kuzmin.bookcatalog.controller

import com.kuzmin.bookcatalog.api.BookController
import com.kuzmin.bookcatalog.model.entity.Book
import com.kuzmin.bookcatalog.service.BookService
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono


@SpringBootTest
class BookControllerIntegrationTests {

    @Test
    fun getBook() {
        val bookService: BookService = Mockito.mock(BookService::class.java)
        `when`(bookService.findByIsbn("9783161484101"))
            .thenReturn(Mono.just(Book(ObjectId.get(), "9783161484101", "Title", "Author", "1991", 9.90)))
        val testClient = WebTestClient.bindToController(
            BookController(bookService)
        ).build()
        testClient.get().uri("/books/9783161484101")
            .exchange()
            .expectStatus().isOk
            .expectBody(Book::class.java)
    }
}