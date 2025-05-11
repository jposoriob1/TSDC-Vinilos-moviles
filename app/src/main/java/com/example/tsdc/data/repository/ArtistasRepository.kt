package com.example.tsdc.data.repository

import com.example.tsdc.data.model.ArtistaDto
import com.example.tsdc.data.model.BandaDto
import com.example.tsdc.data.model.MusicoDto
import com.example.tsdc.data.service.ArtistasService
import com.example.tsdc.utils.CacheManager

class ArtistasRepository(private val service: ArtistasService) {

    suspend fun getMusicos(): List<ArtistaDto> {
        val cached = CacheManager.getInstance().getMusicos()
        if (cached != null && cached.isNotEmpty()) {
            return cached
        }

        val musicos: List<MusicoDto> = service.getMusicos()
        CacheManager.getInstance().setMusicos(musicos)
        return musicos
    }

    suspend fun getBandas(): List<ArtistaDto> {
        val cached = CacheManager.getInstance().getBandas()
        if (cached != null && cached.isNotEmpty()) {
            return cached
        }

        val bandas: List<BandaDto> = service.getBandas()
        CacheManager.getInstance().setBandas(bandas)
        return bandas
    }

    suspend fun getMusicoById(id: Int): MusicoDto {
        val cached = CacheManager.getInstance().getMusicoById(id)
        if (cached != null) {
            return cached as MusicoDto
        }

        val fresh = service.getMusicoById(id)
        CacheManager.getInstance().setMusicoById(id, fresh)
        return fresh
    }

    suspend fun getBandaById(id: Int): BandaDto {
        val cached = CacheManager.getInstance().getBandaById(id)
        if (cached != null) {
            return cached as BandaDto
        }

        val fresh = service.getBandaById(id)
        CacheManager.getInstance().setBandaById(id, fresh)
        return fresh
    }
}

