 package com.example.webfluxdemo.handler

import com.example.webfluxdemo.model.entity.OperatingSystem
import com.example.webfluxdemo.model.dto.OperatingSystemDto
import com.example.webfluxdemo.model.request.OperatingSystemRequest
import com.example.webfluxdemo.service.cloudinstance.OperatingSystemService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

data class Student(val name: String)

@Component
class HelloHandler(
    val operatingSystemService: OperatingSystemService
) {

    fun sayHello(rq: ServerRequest): Mono<ServerResponse> =
        ServerResponse
            .ok()
//            .bodyValue("Hello world")
//            .body(Mono.just("hello world"), String::class.java)
//            .body(Mono.just(Student("choumy")) , Student::class.java)
            .body(
                Flux.just(Student("choumy"), Student("kimhab")),
                Student::class.java
            )

    // path variable example
    fun findStudentById(req: ServerRequest): Mono<ServerResponse> {
        val name = req.pathVariable("name")
        val age = req.pathVariable("age")
        return ServerResponse.ok().body(Mono.just("$name + $age"), String::class.java)
    }

    // query params
    fun search(req: ServerRequest): Mono<ServerResponse> {
        val key = req.queryParam("name")
        return ServerResponse.ok().body(Mono.just(key.get()), String::class.java)
    }

    // request body
    fun createStudent(req: ServerRequest): Mono<ServerResponse> {
        val studentRequestMono = req.bodyToMono(Student::class.java)

        return ServerResponse.ok()
            .body(studentRequestMono, Student::class.java)
    }

    fun createOperatingSystem(req: ServerRequest): Mono<ServerResponse> {
        val osRequestMono = req.bodyToMono(OperatingSystemRequest::class.java)

        val osResultMono: Mono<OperatingSystemDto> =
            osRequestMono.flatMap {
                operatingSystemService.save(it)
            }

        return ServerResponse.ok().body(osResultMono, OperatingSystemDto::class.java)
    }

    fun findAllOS(req:ServerRequest): Mono<ServerResponse> =
        ServerResponse.ok().body(
            operatingSystemService.findAll(),
            OperatingSystemDto::class.java
        )

}