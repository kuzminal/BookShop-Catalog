package com.kuzmin.bookcatalog.grpc

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("!test")
@Component
class GrpcServerRunner(private var grpcServer: GrpcServer) : CommandLineRunner {
    override fun run(vararg args: String?) {
        grpcServer.start()
        grpcServer.block()
    }
}