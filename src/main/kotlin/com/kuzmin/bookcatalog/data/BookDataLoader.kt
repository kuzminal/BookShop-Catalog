package com.kuzmin.bookcatalog.data

import com.kuzmin.bookcatalog.model.entity.Book
import com.kuzmin.bookcatalog.repository.BookRepository
import lombok.RequiredArgsConstructor
import org.bson.types.ObjectId
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.time.Year

@Component
@Profile("local")
@RequiredArgsConstructor
class BookDataLoader(private val bookRepository: BookRepository) {

    @EventListener(ApplicationReadyEvent::class)
    fun loadBookTestData() {
        val book1 = Book(
            ObjectId.get(), "", "Northern Lights", "Lyra Silvertongue",
            Year.of(2011), 9.90
        )
        val book2 = Book(
            ObjectId.get(), "", "Polar Journey", "Iorek Polarson",
            Year.of(1993), 12.90
        )
        bookRepository.save(book1)
        bookRepository.save(book2)
    }
}