package com.kuzmin.bookcatalog.service

import com.kuzmin.bookcatalog.model.entity.Book
import com.kuzmin.bookcatalog.repository.BookRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class BookService(val bookRepository: BookRepository) : IBookService {
    override fun save(book: Mono<Book>): Mono<Book> = book.flatMap { bookFromRequest ->
        existsByIsbn(bookFromRequest.isbn).flatMap { exist ->
            if (!exist)
                bookRepository.save(bookFromRequest)
            else
                Mono.just(bookFromRequest)
        }
    }

    override fun search(title: String): Flux<Book> =
        if (title.isNotBlank()) bookRepository.findAllByTitle(title)
        else bookRepository.findAll(Sort.by(Sort.Order.asc("title")))

    override fun findByIsbn(isbn: String): Mono<Book> = bookRepository.findByIsbn(isbn)

    override fun existsByIsbn(isbn: String): Mono<Boolean> = bookRepository.existsByIsbn(isbn)

    override fun deleteByIsbn(isbn: String): Mono<Boolean> = bookRepository.deleteByIsbn(isbn)
}