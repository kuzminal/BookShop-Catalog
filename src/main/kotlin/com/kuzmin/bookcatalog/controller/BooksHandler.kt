package com.kuzmin.bookcatalog.controller

import com.kuzmin.bookcatalog.model.entity.Book
import com.kuzmin.bookcatalog.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import reactor.core.publisher.Mono
import java.net.URI

@Component
class BooksHandler(val bookService: BookService) {
    fun create(serverRequest: ServerRequest) =
        bookService.save(serverRequest.bodyToMono()).flatMap {
            ServerResponse.created(URI.create("/books/${it.isbn}")).build()
        }

    fun search(serverRequest: ServerRequest) =
        ServerResponse.ok().body(
            bookService.search(
                serverRequest.queryParam("title")
                    .orElse("")
            ), Book::class.java
        )

    fun deleteByIsbn(serverRequest: ServerRequest) =
        bookService.deleteByIsbn(serverRequest.pathVariable("isbn")).flatMap {
            if (it) ServerResponse.ok().build()
            else ServerResponse.status(HttpStatus.NOT_FOUND).build()
        }

    fun findByIsbn(serverRequest: ServerRequest) =
        ServerResponse.ok().body(
            bookService.findByIsbn(
                serverRequest.queryParam("isbn")
                    .orElse("")
            ), Book::class.java
        )
}