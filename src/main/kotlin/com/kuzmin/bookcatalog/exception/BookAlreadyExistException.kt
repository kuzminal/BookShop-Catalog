package com.kuzmin.bookcatalog.exception

class BookAlreadyExistException(isbn: String) : RuntimeException("A book with ISBN $isbn already exists.") {
}