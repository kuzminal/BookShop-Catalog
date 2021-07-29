package com.kuzmin.bookcatalog.api

import com.kuzmin.bookcatalog.model.entity.Book
import com.kuzmin.bookcatalog.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/books")
class BookController(val bookService: BookService) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAll(@RequestParam(required = false, defaultValue = "") title:String) = bookService.search(title)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBook(@Validated @RequestBody book: Mono<Book>) = bookService.save(book)

    @DeleteMapping("/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteBook(@PathVariable isbn: String) = bookService.deleteByIsbn(isbn)

    @GetMapping("/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    fun getByIsbn(@PathVariable isbn: String) = bookService.findByIsbn(isbn)
}