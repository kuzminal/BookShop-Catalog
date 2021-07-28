package com.kuzmin.bookcatalog.service

import com.kuzmin.bookcatalog.model.entity.Book
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface IBookService {
    fun save(book: Mono<Book>): Mono<Book>
    fun search(title: String): Flux<Book>
    fun findByIsbn(isbn: String): Mono<Book>
    fun existsByIsbn(isbn: String): Mono<Boolean>
    fun deleteByIsbn(isbn: String): Mono<Boolean>
}