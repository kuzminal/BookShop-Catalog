package com.kuzmin.bookcatalog.model.entity

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.index.TextIndexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.redis.core.RedisHash
import java.time.Year
import javax.validation.constraints.*

@Document(collection = "books")
@NoArgsConstructor
@AllArgsConstructor
@Data
data class Book(
    @JsonProperty("id")
    @Id val id: String,
    @JsonProperty("isbn")
    @field:NotBlank(message = "The book ISBN must be defined.")
    @field:Pattern(
        regexp = "^(97([89]))?\\d{9}(\\d|X)$",
        message = "The ISBN format must follow the standards ISBN-10 or ISBN-13."
    )
    @Indexed(unique = true)
    val isbn: String = "",
    @field:NotBlank(message = "The book title must be defined.")
    @TextIndexed
    @JsonProperty("title")
    val title: String = "",
    @field:NotBlank(message = "The book author must be defined.")
    @TextIndexed
    @JsonProperty("author")
    val author: String = "",
    //@field:PastOrPresent(message = "Year must be past or present time")
    @JsonProperty("publishingYear")
    val publishingYear: Int =2000,
    @field:NotNull(message = "The book price must be defined.")
    @field:Positive(message = "The book price must be greater than zero.")
    @JsonProperty("price")
    val price: Double = 0.0,
    @NotNull
    @Min(1)
    @Max(5)
    @JsonProperty("quantity")
    val quantity: Int  = 1,
    @Version
    @JsonProperty("version")
    private var version: Int = 0
) {
}