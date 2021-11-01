package com.kuzmin.bookcatalog.grpc

import com.kuzmin.bookcatalog.service.BookService
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.ServerServiceDefinition
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.IOException




@Component
class GrpcServer(private val bookService: BookService) {

    private val LOG: Logger = LoggerFactory.getLogger(javaClass)

    @Value("\${grpc.port:50051}")
    private val port = 0

    private var server: Server? = null


    @Throws(IOException::class, InterruptedException::class)
    fun start() {
        LOG.info("gRPC server is starting on port: {}.", port)
        server = ServerBuilder.forPort(port)
            .addService(BookCatalogueImpl(bookService))
            //.intercept(exceptionInterceptor)
            .build().start()
        LOG.info("gRPC server started and listening on port: {}.", port)
        LOG.info("Following service are available: ")
        server!!.services.stream()
            .forEach { s: ServerServiceDefinition ->
                LOG.info(
                    "Service Name: {}",
                    s.serviceDescriptor.name
                )
            }
        Runtime.getRuntime().addShutdownHook(Thread {
            LOG.info("Shutting down gRPC server.")
            stop()
            LOG.info("gRPC server shut down successfully.")
        })
    }

    private fun stop() {
        if (server != null) {
            server!!.shutdown()
        }
    }

    @Throws(InterruptedException::class)
    fun block() {
        if (server != null) {
            server!!.awaitTermination()
        }
    }
}