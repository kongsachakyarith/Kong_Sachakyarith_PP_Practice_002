package com.example.webfluxdemo.repository

import com.example.webfluxdemo.model.entity.OperatingSystem
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface OperatingSystemRepository : ReactiveCrudRepository<OperatingSystem,Long> {
}