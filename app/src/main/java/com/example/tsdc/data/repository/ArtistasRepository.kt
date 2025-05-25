package com.example.tsdc.data.repository

import com.example.tsdc.data.local.dao.BandasDao
import com.example.tsdc.data.local.dao.MusicosDao
import com.example.tsdc.data.local.entity.Banda
import com.example.tsdc.data.local.entity.Musico
import com.example.tsdc.data.model.ArtistaDto
import com.example.tsdc.data.model.BandaDto
import com.example.tsdc.data.model.MusicoDto
import com.example.tsdc.data.service.ArtistasService
import com.example.tsdc.utils.CacheManager

class ArtistasRepository(
    private val service: ArtistasService,
    private val musicosDao: MusicosDao,
    private val bandasDao: BandasDao
) {

    suspend fun getMusicos(): List<ArtistaDto> {
        val cache = CacheManager.getInstance().getMusicos()
        if (cache != null && cache.isNotEmpty()) return cache

        val local = musicosDao.getMusicos()
        if (local.isNotEmpty()) {
            val musicos = local.map {
                MusicoDto(
                    id = it.id,
                    name = it.name,
                    image = it.image,
                    description = it.description,
                    birthDate = it.birthDate,
                    albums = emptyList(),
                    performerPrizes = emptyList()
                )
            }
            CacheManager.getInstance().setMusicos(musicos)
            return musicos
        }

        val fresh = service.getMusicos()
        CacheManager.getInstance().setMusicos(fresh)

        val localToSave = fresh.map {
            Musico(
                id = it.id,
                name = it.name,
                image = it.image,
                description = it.description,
                birthDate = it.birthDate
            )
        }

        musicosDao.insertAll(localToSave)

        return fresh
    }

    suspend fun getBandas(): List<ArtistaDto> {
        val cache = CacheManager.getInstance().getBandas()
        if (cache != null && cache.isNotEmpty()) return cache

        val local = bandasDao.getBandas()
        if (local.isNotEmpty()) {
            val bandas = local.map {
                BandaDto(
                    id = it.id,
                    name = it.name,
                    image = it.image,
                    description = it.description,
                    creationDate = it.creationDate,
                    albums = emptyList(),
                    musicians = emptyList(),
                    performerPrizes = emptyList()
                )
            }
            CacheManager.getInstance().setBandas(bandas)
            return bandas
        }

        val fresh = service.getBandas()
        CacheManager.getInstance().setBandas(fresh)

        val localToSave = fresh.map {
            Banda(
                id = it.id,
                name = it.name,
                image = it.image,
                description = it.description,
                creationDate = it.creationDate
            )
        }

        bandasDao.insertAll(localToSave)

        return fresh
    }

    suspend fun getMusicoById(id: Int): MusicoDto {
        val cached = CacheManager.getInstance().getMusicoById(id)
        if (cached != null) return cached as MusicoDto

        val fresh = service.getMusicoById(id)
        CacheManager.getInstance().setMusicoById(id, fresh)
        return fresh
    }

    suspend fun getBandaById(id: Int): BandaDto {
        val cached = CacheManager.getInstance().getBandaById(id)
        if (cached != null) return cached as BandaDto

        val fresh = service.getBandaById(id)
        CacheManager.getInstance().setBandaById(id, fresh)
        return fresh
    }

    suspend fun refreshMusicos() {
        val fresh = service.getMusicos()
        musicosDao.clearAll()
        val local = fresh.map {
            Musico(
                id = it.id,
                name = it.name,
                image = it.image,
                description = it.description,
                birthDate = it.birthDate
            )
        }
        musicosDao.insertAll(local)
    }

    suspend fun refreshBandas() {
        val fresh = service.getBandas()
        bandasDao.clearAll()
        val local = fresh.map {
            Banda(
                id = it.id,
                name = it.name,
                image = it.image,
                description = it.description,
                creationDate = it.creationDate
            )
        }
        bandasDao.insertAll(local)
    }
}
