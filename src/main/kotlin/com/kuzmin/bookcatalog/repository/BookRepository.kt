package com.kuzmin.bookcatalog.repository

import com.kuzmin.bookcatalog.model.entity.Book
import org.bson.types.ObjectId
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface BookRepository: ReactiveSortingRepository<Book, ObjectId> {
    fun findAllByTitle(title: String): Flux<Book>
    fun findByIsbn(isbn: String): Mono<Book>
    fun existsByIsbn(isbn: String): Mono<Boolean>
    fun deleteByIsbn(isbn: String): Mono<Long>
}