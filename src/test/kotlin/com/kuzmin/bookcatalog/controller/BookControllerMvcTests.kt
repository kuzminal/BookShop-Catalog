package com.kuzmin.bookcatalog.controller

import com.kuzmin.bookcatalog.api.BookController
import com.kuzmin.bookcatalog.exception.BookNotFoundException
import com.kuzmin.bookcatalog.service.BookService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@AutoConfigureWebMvc
@WebMvcTest(
    BookController::class)
class BookControllerMvcTests(@MockBean val bookService: BookService) {
    @Autowired
    private val mockMvc: MockMvc? = null

    @Test
    @Throws(Exception::class)
    fun whenReadingNotExistingBookThenShouldReturn404() {
        val isbn = "73737313940"
        given(bookService.findByIsbn(isbn)).willThrow(BookNotFoundException::class.java)
        mockMvc
            ?.perform(get("/books/$isbn"))
            ?.andExpect(status().isNotFound)
    }
}