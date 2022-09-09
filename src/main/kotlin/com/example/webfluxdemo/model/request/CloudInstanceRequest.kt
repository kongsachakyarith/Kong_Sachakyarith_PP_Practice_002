package com.example.webfluxdemo.model.request

import com.example.webfluxdemo.model.entity.CloudInstance
import kotlin.random.Random

data class CloudInstanceRequest(
    val instanceName: String,
    val operatingSystemId: Long
){
    fun toEntity() = CloudInstance(
        instanceName = instanceName,
        operatingSystemId = operatingSystemId,
        publicIpAddress = "${Random.nextInt(0,256)}.${Random.nextInt(0,256)}.${Random.nextInt(0,256)}.${Random.nextInt(0,256)}"
    )
}