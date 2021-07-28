package com.kuzmin.bookcatalog.controller

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.router

@Component
class BooksRouter(private val booksHandler: BooksHandler) {
    @Bean
    fun customerRoutes() = router {
        "/books".nest {
            POST("/", booksHandler::create)
            GET("/", booksHandler::search)
        }
    }
}