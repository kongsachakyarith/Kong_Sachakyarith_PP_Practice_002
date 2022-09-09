package com.example.webfluxdemo.model.dto

data class CloudInstanceDto(
    val id: Long,
    val instanceName: String,
    val publicIpAddress: String,
    var operatingSystem: OperatingSystemDto? = null
)

