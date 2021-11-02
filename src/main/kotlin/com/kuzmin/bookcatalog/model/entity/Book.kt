package com.kuzmin.bookcatalog.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.index.TextIndexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Year
import javax.validation.constraints.*

@Document(collection = "books")
data class Book(
    @Id val id: String,
    @field:NotBlank(message = "The book ISBN must be defined.")
    @field:Pattern(
        regexp = "^(97([89]))?\\d{9}(\\d|X)$",
        message = "The ISBN format must follow the standards ISBN-10 or ISBN-13."
    )
    @Indexed(unique = true)
    val isbn: String = "",
    @field:NotBlank(message = "The book title must be defined.")
    @TextIndexed
    val title: String = "",
    @field:NotBlank(message = "The book author must be defined.")
    @TextIndexed
    val author: String = "",
    @field:PastOrPresent(message = "Year must be past or present time")
    val publishingYear: Year = Year.of(2000),
    @field:NotNull(message = "The book price must be defined.")
    @field:Positive(message = "The book price must be greater than zero.")
    val price: Double = 0.0,
    @NotNull
    @Min(1)
    @Max(5)
    val quantity: Int  = 1,
    @Version
    private var version: Int = 0
) {
}