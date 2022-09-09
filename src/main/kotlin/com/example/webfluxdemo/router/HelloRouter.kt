package com.example.webfluxdemo.router

import com.example.webfluxdemo.handler.CloudInstanceHandler
import com.example.webfluxdemo.handler.HelloHandler
import com.example.webfluxdemo.handler.OSHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class HelloRouter(val handler: HelloHandler, val cloudInstanceHandler: CloudInstanceHandler, val osHandler: OSHandler) {

    @Bean
    fun helloWorld(): RouterFunction<ServerResponse> =
        router {
            GET("/api/v1/test", handler::sayHello)
            GET("/api/v1/students/{name}/{age}", handler::findStudentById)
            GET("/api/v1/students/search", handler::search)
            POST("/api/v1/students", handler::createStudent)
        }

    @Bean
    fun osRouter(): RouterFunction<ServerResponse> =
        router {
            "/api/v1".nest {
                POST("/os", osHandler::createOperatingSystem)
                GET("/os", osHandler::findAllOS)
                POST("/instance", cloudInstanceHandler::createCloudInstance)
                GET("/instance", cloudInstanceHandler::findAllCloudInstances)
                GET("/instance/users/{id}", cloudInstanceHandler::getAllCloudUsers)
            }
        }
}