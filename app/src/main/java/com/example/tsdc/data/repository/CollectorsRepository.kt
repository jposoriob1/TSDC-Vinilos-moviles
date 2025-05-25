package com.example.tsdc.data.repository

import com.example.tsdc.data.local.dao.CollectorsDao
import com.example.tsdc.data.local.entity.Collector
import com.example.tsdc.data.model.CollectorDto
import com.example.tsdc.data.service.CollectorsService
import com.example.tsdc.utils.CacheManager

class CollectorsRepository(
    private val collectorsService: CollectorsService,
    private val collectorsDao: CollectorsDao
) {

    suspend fun getCollectors(): List<CollectorDto> {
        val cache = CacheManager.getInstance().getCollectors()
        if (cache != null && cache.isNotEmpty()) return cache

        val local = collectorsDao.getCollectors()
        if (local.isNotEmpty()) {
            val collectors = local.map {
                CollectorDto(
                    id = it.id,
                    name = it.name,
                    telephone = it.telephone,
                    email = it.email,
                    comments = emptyList(),
                    favoritePerformers = emptyList(),
                    collectorAlbums = emptyList()
                )
            }
            CacheManager.getInstance().setCollectors(collectors)
            return collectors
        }

        val fresh = collectorsService.getCollectors()
        CacheManager.getInstance().setCollectors(fresh)

        val localToSave = fresh.map {
            Collector(
                id = it.id,
                name = it.name,
                telephone = it.telephone,
                email = it.email
            )
        }

        collectorsDao.insertAll(localToSave)

        return fresh
    }

    suspend fun getCollectorById(id: Int): CollectorDto {
        val cached = CacheManager.getInstance().getCollectorById(id)
        if (cached != null) return cached

        val fresh = collectorsService.getCollectorById(id)
        CacheManager.getInstance().setCollectorById(id, fresh)
        return fresh
    }

    suspend fun refreshCollectors() {
        val fresh = collectorsService.getCollectors()
        collectorsDao.clearAll()

        val localToSave = fresh.map {
            Collector(
                id = it.id,
                name = it.name,
                telephone = it.telephone,
                email = it.email
            )
        }

        collectorsDao.insertAll(localToSave)
    }
}
