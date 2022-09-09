package com.example.webfluxdemo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class UserClient {

    @Bean("UserClient")
    fun userClient(): WebClient =
        WebClient
            .builder()
            .baseUrl("https://spring-reactive.hrd-edu.info")
            .build()

}