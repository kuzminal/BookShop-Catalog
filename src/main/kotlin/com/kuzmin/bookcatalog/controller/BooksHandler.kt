package com.kuzmin.bookcatalog.controller

import com.kuzmin.bookcatalog.model.entity.Book
import com.kuzmin.bookcatalog.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import java.net.URI

@Component
class BooksHandler(val bookService: BookService) {
    fun create(serverRequest: ServerRequest) =
        bookService.save(serverRequest.bodyToMono()).flatMap {
            ServerResponse.created(URI.create("/books/${it.id}")).build()
        }

    fun search(serverRequest: ServerRequest) =
        ServerResponse.ok().body(
            bookService.search(
                serverRequest.queryParam("title")
                    .orElse("")
            ), Book::class.java
        )
}