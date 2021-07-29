package com.kuzmin.bookcatalog.service

import com.kuzmin.bookcatalog.exception.BookAlreadyExistException
import com.kuzmin.bookcatalog.exception.BookNotFoundException
import com.kuzmin.bookcatalog.model.entity.Book
import com.kuzmin.bookcatalog.repository.BookRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class BookService(val bookRepository: BookRepository) : IBookService {
    override fun save(book: Mono<Book>): Mono<Book> = book.flatMap { bookFromRequest ->
        existsByIsbn(bookFromRequest.isbn).flatMap { exist ->
            if (!exist)
                bookRepository.save(bookFromRequest)
            else
                Mono.error(BookAlreadyExistException(bookFromRequest.isbn))
        }
    }

    override fun search(title: String): Flux<Book> =
        if (title.isNotBlank()) bookRepository.findAllByTitle(title)
        else bookRepository.findAll(Sort.by(Sort.Order.asc("title")))

    override fun findByIsbn(isbn: String): Mono<Book> = bookRepository.findByIsbn(isbn).switchIfEmpty { Mono.error(BookNotFoundException(isbn)) }


    override fun existsByIsbn(isbn: String): Mono<Boolean> = bookRepository.existsByIsbn(isbn)

    override fun deleteByIsbn(isbn: String): Mono<Boolean> =
        existsByIsbn(isbn).flatMap { exist ->
            if (exist) {
                bookRepository.deleteByIsbn(isbn).map {
                    it > 0L
                }
            } else {
                Mono.error(BookNotFoundException(isbn))
            }
        }
}