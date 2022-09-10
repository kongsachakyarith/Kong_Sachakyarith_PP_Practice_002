package com.example.webfluxdemo.service.operatingsystem

import com.example.webfluxdemo.handler.AppUser
import com.example.webfluxdemo.model.dto.CloudInstanceDto
import com.example.webfluxdemo.model.request.CloudInstanceRequest
import com.example.webfluxdemo.repository.CloudInstanceRepository
import com.example.webfluxdemo.repository.OperatingSystemRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CloudInstanceServiceImpl(
    val cloudInstanceRepository: CloudInstanceRepository,
    val operatingSystemRepository: OperatingSystemRepository,
    @Qualifier("UserClient") val userClient: WebClient
) : CloudInstanceService {
    override fun create(cloudInstanceRequest: CloudInstanceRequest): Mono<CloudInstanceDto> {
        return cloudInstanceRepository
            .save(cloudInstanceRequest.toEntity())
            .map { res -> res.toDto() }
    }

    override fun findAll(): Flux<CloudInstanceDto> {
        val cloudInstanceFlux = cloudInstanceRepository
            .findAll()

        val osFlux = cloudInstanceFlux
            .flatMap {
                operatingSystemRepository.findById(it.operatingSystemId)
            }

        val ownerIdFlux: Flux<String> = cloudInstanceFlux.map { it.owner }

        fun findUserById(id: String): Mono<AppUser> = userClient.get()
            .uri("/api/v1/users/{id}", id)
            .retrieve()
            .bodyToMono(AppUser::class.java)

        val cloudOsFlux = cloudInstanceFlux.zipWith(osFlux)
            .map {
                val cloud = it.t1
                val myos = it.t2

                val cloudResponse = cloud.toDto()
                cloudResponse.operatingSystem = myos.toDto()

                cloudResponse
            }
        return cloudOsFlux.zipWith(ownerIdFlux)
            .flatMap {
                val cloud = it.t1
                val ownerId = it.t2
                val owner = findUserById(ownerId)

                Mono.just(cloud).zipWith(owner)
                    .map { resultTup ->
                        val clouds = resultTup.t1
                        val ownerResult = resultTup.t2
                        clouds.owner = ownerResult


                        clouds
                    }
                    .log()
            }
    }


}