package com.kuzmin.bookcatalog.controller

import com.kuzmin.bookcatalog.api.BookController
import com.kuzmin.bookcatalog.model.entity.Book
import com.kuzmin.bookcatalog.service.BookService
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.util.StreamUtils
import reactor.core.publisher.Mono
import java.nio.charset.Charset


@SpringBootTest
class BookControllerIntegrationTests {

    @Test
    fun getBook() {
        val bookService: BookService = Mockito.mock(BookService::class.java)
        val book = Book(ObjectId("6104032d71e8ba05acfdebbf"), "9783161484101", "Title", "Author", "1991", 9.90);
        `when`(bookService.findByIsbn("9783161484101"))
            .thenReturn(Mono.just(book))
        val testClient = WebTestClient.bindToController(
            BookController(bookService)
        ).build()
        val byIsbnResource = ClassPathResource("/books/book-by-isbn.json")
        val byIsbnJson: String = StreamUtils.copyToString(
            byIsbnResource.inputStream, Charset.defaultCharset()
        )
        testClient.get().uri("/books/9783161484101")
            .exchange()
            .expectStatus().isOk
            .expectBody().json(byIsbnJson)
    }
}