package com.kuzmin.bookcatalog.model.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.format.annotation.DateTimeFormat
import java.time.Year
import javax.validation.constraints.*

@Document(collection = "books")
data class Book(
    @Id val id: ObjectId?,
    @field:NotBlank(message = "The book ISBN must be defined.")
    @field:Pattern(regexp = "^(97([89]))?\\d{9}(\\d|X)$", message = "The ISBN format must follow the standards ISBN-10 or ISBN-13.")
    @Indexed(unique = true)
    val isbn: String = "",
    @field:NotBlank(message = "The book title must be defined.")
    val title: String = "",
    @field:NotBlank(message = "The book author must be defined.")
    val author: String = "",
    @field:PastOrPresent(message = "The book cannot have been published in the future.")
    @field:DateTimeFormat
    val publishingYear: Year = Year.now(),
    @field:NotNull(message = "The book price must be defined.")
    @field:Positive(message = "The book price must be greater than zero.")
    val price: Double = 0.0
    ) {
}