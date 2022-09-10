package com.example.webfluxdemo.service.operatingsystem

import com.example.webfluxdemo.model.dto.CloudInstanceDto
import com.example.webfluxdemo.model.request.CloudInstanceRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CloudInstanceService {

    fun create(cloudInstanceRequest: CloudInstanceRequest): Mono<CloudInstanceDto>

    fun findAll(): Flux<CloudInstanceDto>


}