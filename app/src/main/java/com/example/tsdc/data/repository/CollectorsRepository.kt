package com.example.tsdc.data.repository

import com.example.tsdc.data.model.CollectorDto
import com.example.tsdc.data.service.CollectorsService
import com.example.tsdc.utils.CacheManager

class CollectorsRepository(private val collectorsService: CollectorsService) {

    suspend fun getCollectors(): List<CollectorDto> {
        val cached = CacheManager.getInstance().getCollectors()
        if (cached != null && cached.isNotEmpty()) {
            return cached
        }

        val fresh = collectorsService.getCollectors()
        CacheManager.getInstance().setCollectors(fresh)
        return fresh
    }

    suspend fun getCollectorById(id: Int): CollectorDto {
        val cached = CacheManager.getInstance().getCollectorById(id)
        if (cached != null) {
            return cached
        }

        val fresh = collectorsService.getCollectorById(id)
        CacheManager.getInstance().setCollectorById(id, fresh)
        return fresh
    }
}
