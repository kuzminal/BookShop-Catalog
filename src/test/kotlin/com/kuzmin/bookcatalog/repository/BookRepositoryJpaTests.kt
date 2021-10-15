package com.kuzmin.bookcatalog.repository

import com.kuzmin.bookcatalog.model.converter.YearAttributeConverter
import com.kuzmin.bookcatalog.model.entity.Book
import org.bson.types.ObjectId
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.Year

@Testcontainers
@Import(YearAttributeConverter::class)
@DataMongoTest(excludeAutoConfiguration = [EmbeddedMongoAutoConfiguration::class])
class BookRepositoryJpaTests(
    @Autowired private val bookRepository: BookRepository
) {

    companion object {
        @Container
        var mongoDBContainer = MongoDBContainer("mongo:4.4.2")

        @DynamicPropertySource
        fun setProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri") { mongoDBContainer.replicaSetUrl }
        }
    }

    @AfterEach
    fun cleanUp() {
        this.bookRepository.deleteAll()
    }
    @Test
    fun findBookByIsbnWhenExisting() {
        val bookIsbn = "1234561235"
        val expectedBook = Book(ObjectId("6104032d71e8ba05acfdebbf"), bookIsbn, "Title", "Author", Year.of(2000), 12.90)
        val actualBook: Mono<Book> = bookRepository.findByIsbn(bookIsbn)
        StepVerifier
            .create(actualBook)
            .expectNext(expectedBook)
            .expectComplete()
            .verify()
    }
}