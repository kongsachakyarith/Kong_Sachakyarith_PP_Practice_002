package com.example.webfluxdemo.service.cloudinstance

import com.example.webfluxdemo.model.dto.OperatingSystemDto
import com.example.webfluxdemo.model.entity.OperatingSystem
import com.example.webfluxdemo.model.request.OperatingSystemRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface OperatingSystemService {

    fun save(os: OperatingSystemRequest): Mono<OperatingSystemDto>

    fun findAll(): Flux<OperatingSystemDto>
    fun findById(operatingSystemId: Long): Mono<OperatingSystem>
}