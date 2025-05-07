package com.example.tsdc.data.service

import com.example.tsdc.data.model.BandaDto
import com.example.tsdc.data.model.MusicoDto
import retrofit2.http.GET

interface ArtistasService {

    @GET("musicians")
    suspend fun getMusicos(): List<MusicoDto>

    @GET("bands")
    suspend fun getBandas(): List<BandaDto>
}