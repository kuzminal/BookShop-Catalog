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
    override fun save(@RequestBody book: Mono<Book>): Mono<Book> = book.flatMap(bookRepository::save);
    override fun search(@RequestParam(name = "title") title: String): Flux<Book> {
        return if (title.isNotBlank()) bookRepository.findAllByTitle(title) else bookRepository.findAll(
            Sort.by(
                Sort.Order.asc(
                    "title"
                )
            )
        )
    }
}