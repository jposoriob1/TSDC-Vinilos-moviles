package com.example.tsdc.data.repository

import com.example.tsdc.data.model.CollectorDto
import com.example.tsdc.data.service.CollectorsService

class CollectorsRepository(private val collectorsService: CollectorsService) {

    suspend fun getCollectors(): List<CollectorDto> {
        return collectorsService.getCollectors()
    }

    suspend fun getCollectorById(id: Int): CollectorDto {
        return collectorsService.getCollectorById(id)
    }
}