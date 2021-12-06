package com.kuzmin.bookcatalog.grpc

import bookcatalogue.BookCatalogueGrpc
import bookcatalogue.BookCatalogueGrpcKt
import bookcatalogue.ProductInfo
import com.kuzmin.bookcatalog.service.BookService
import io.grpc.Status
import io.grpc.StatusException
import io.grpc.stub.StreamObserver
import org.springframework.stereotype.Component

@Component
class BookCatalogueImpl(private val bookService: BookService) : BookCatalogueGrpc.BookCatalogueImplBase() {
    override fun getBook(
        request: ProductInfo.BookIsbn,
        responseObserver: StreamObserver<ProductInfo.Book?>
    ) {
        val isbn: String = request.isbn
        bookService.existsByIsbn(isbn).subscribe { exist ->
            if (exist) {
                bookService.findByIsbn(isbn).subscribe { book ->
                    responseObserver.onNext(
                        ProductInfo.Book.newBuilder()
                            .setIsbn(book.isbn)
                            .setAuthor(book.author)
                            .setPrice(book.price)
                            .setTitle(book.title)
                            .setPublishingYear(book.publishingYear)
                            .setQuantity(book.quantity)
                            .build()
                    )
                    responseObserver.onCompleted()
                }
            } else {
                responseObserver.onError(StatusException(Status.NOT_FOUND))
            }
        }
    }
}