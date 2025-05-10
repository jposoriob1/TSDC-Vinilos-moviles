package com.example.tsdc.data.repository

import com.example.tsdc.data.model.ArtistaDto
import com.example.tsdc.data.model.BandaDto
import com.example.tsdc.data.model.MusicoDto
import com.example.tsdc.data.service.ArtistasService

class ArtistasRepository(private val service: ArtistasService) {

    suspend fun getMusicos(): List<ArtistaDto> {
        val musicos: List<MusicoDto> = service.getMusicos()
        return musicos
    }

    suspend fun getBandas(): List<ArtistaDto> {
        val bandas: List<BandaDto> = service.getBandas()
        return bandas
    }

    suspend fun getMusicoById(id: Int): MusicoDto {
        return service.getMusicoById(id)
    }

    suspend fun getBandaById(id: Int): BandaDto {
        return service.getBandaById(id)
    }
}

