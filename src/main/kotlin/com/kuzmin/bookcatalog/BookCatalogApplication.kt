package com.kuzmin.bookcatalog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableReactiveMongoRepositories
class BookCatalogApplication

fun main(args: Array<String>) {
    runApplication<BookCatalogApplication>(*args)
}
