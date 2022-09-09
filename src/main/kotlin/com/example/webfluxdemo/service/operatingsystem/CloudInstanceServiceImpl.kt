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
class  CloudInstanceServiceImpl(
    val cloudInstanceRepository: CloudInstanceRepository,
    val operatingSystemRepository: OperatingSystemRepository,
) : CloudInstanceService {
    override fun create(cloudInstanceRequest: CloudInstanceRequest): Mono<CloudInstanceDto> {
        return cloudInstanceRepository
            .save(cloudInstanceRequest.toEntity())
            .map { res -> res.toDto() }
    }



    override fun findAll(): Flux<CloudInstanceDto> {
        val cloudInstanceMono = cloudInstanceRepository
            .findAll()

        val osMono = cloudInstanceMono
            .flatMap {
                operatingSystemRepository.findById(it.operatingSystemId)
            }



        return cloudInstanceMono.zipWith(osMono)
            .map {
                val cloud = it.t1
                val myos = it.t2

                val cloudResponse = cloud.toDto()
                cloudResponse.operatingSystem = myos.toDto()

                cloudResponse
            }
    }




}