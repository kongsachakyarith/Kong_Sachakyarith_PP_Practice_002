package com.example.webfluxdemo.handler

import com.example.webfluxdemo.model.dto.CloudInstanceDto
import com.example.webfluxdemo.model.request.CloudInstanceRequest
import com.example.webfluxdemo.service.operatingsystem.CloudInstanceService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.util.Date

data class AppUser(
    val id: String,
    val name: String,
    val email: String,
    val image: String,
    val createdDate: Date,
    val lastModified: Date
)

@Component
class CloudInstanceHandler(
    val cloudInstanceService: CloudInstanceService,
    @Qualifier("UserClient") val userClient: WebClient
) {

    fun getAllCloudUsers(req: ServerRequest): Mono<ServerResponse> {
        val userId = req.pathVariable("id")

        val userFlux = userClient.get()
            .uri("/api/v1/users/{id}", userId)
            .retrieve()
            .bodyToMono(AppUser::class.java)

        return ServerResponse.ok().body(userFlux, AppUser::class.java)
    }

    fun createCloudInstance(req: ServerRequest): Mono<ServerResponse> =
        req.bodyToMono(CloudInstanceRequest::class.java)
            .flatMap {
                cloudInstanceService.create(it)
            }
            .flatMap {
                ServerResponse.ok().bodyValue(it)
            }

    fun findAllCloudInstances(req: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok()
            .body(
                cloudInstanceService.findAll(),
                CloudInstanceDto::class.java
            )
    }


}