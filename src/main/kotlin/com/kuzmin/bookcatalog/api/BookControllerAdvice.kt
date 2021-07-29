package com.kuzmin.bookcatalog.api

import com.kuzmin.bookcatalog.exception.BookAlreadyExistException
import com.kuzmin.bookcatalog.exception.BookNotFoundException
import com.kuzmin.bookcatalog.exception.Error
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import java.util.function.Consumer

@RestControllerAdvice
class BookControllerAdvice {
    @ExceptionHandler(BookNotFoundException::class)
    fun bookNotFoundHandler(ex: BookNotFoundException): ResponseEntity<Error> =
        ResponseEntity(Error(ex.message.orEmpty()), HttpStatus.NOT_FOUND)

    @ExceptionHandler(BookAlreadyExistException::class)
    fun bookAlreadyExistsHandler(ex: BookAlreadyExistException): ResponseEntity<Error> =
        ResponseEntity(Error(ex.message.orEmpty()), HttpStatus.UNPROCESSABLE_ENTITY)

    @ExceptionHandler(WebExchangeBindException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: WebExchangeBindException): MutableMap<String, String> {
        val errorsMap: MutableMap<String, String> = HashMap()
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            errorsMap[fieldName] = errorMessage.orEmpty()
        })
        return errorsMap
    }
}