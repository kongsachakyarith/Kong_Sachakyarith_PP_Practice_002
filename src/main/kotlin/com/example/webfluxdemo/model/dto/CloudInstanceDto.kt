package com.example.webfluxdemo.model.dto

import com.example.webfluxdemo.handler.AppUser


data class CloudInstanceDto(
    val id: Long,
    val instanceName: String,
    val publicIpAddress: String,
    var operatingSystem: OperatingSystemDto? = null,
    var owner :AppUser? = null

)

