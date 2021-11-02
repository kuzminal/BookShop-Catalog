package com.kuzmin.bookcatalog.validation

import com.kuzmin.bookcatalog.model.entity.Book
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import java.time.Year
import javax.validation.ConstraintViolation
import javax.validation.Validation

@ContextConfiguration
class BookValidationTests {
    private var validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun whenAllFieldsCorrectThenValidationSucceeds() {
        val book = Book("6104032d71e8ba05acfdebbf", "9783161484100","Title", "Author", Year.of(2000), 9.90)
        val violations: Set<ConstraintViolation<Book>> = validator.validate(book)
        assertThat(violations).isEmpty()
    }

    @Test
    fun whenIsbnDefinedButIncorrectThenValidationFails() {
        val book = Book("6104032d71e8ba05acfdebbf", "978-3-16-148410-0","Title", "Author", Year.of(2000), 9.90)
        val violations: Set<ConstraintViolation<Book>> = validator.validate(book)
        assertThat(violations).hasSize(1)
        assertThat(violations.iterator().next().message)
            .isEqualTo("The ISBN format must follow the standards ISBN-10 or ISBN-13.")
    }
}