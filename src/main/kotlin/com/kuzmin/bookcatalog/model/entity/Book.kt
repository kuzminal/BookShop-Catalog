package com.kuzmin.bookcatalog.model.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "books")
data class Book(
    @Id val id: ObjectId?,
    val isbn: String = "",
    val title: String = "",
    val author: String = "",
    val publishingYear: String = "",
    val price: Double = 0.0
    ) {
}