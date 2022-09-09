package com.example.webfluxdemo.model.request

import com.example.webfluxdemo.model.entity.OperatingSystem

data class OperatingSystemRequest(
    val name: String,
    val version: String
){
    fun toEntity() = OperatingSystem(
        name = name,
        version = version
    )
}
