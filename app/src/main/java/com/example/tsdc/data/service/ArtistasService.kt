package com.example.tsdc.data.service

import com.example.tsdc.data.model.BandaDto
import com.example.tsdc.data.model.MusicoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ArtistasService {

    @GET("musicians")
    suspend fun getMusicos(): List<MusicoDto>

    @GET("bands")
    suspend fun getBandas(): List<BandaDto>

    @GET("musicians/{id}")
    suspend fun getMusicoById(@Path("id") id: Int): MusicoDto

    @GET("bands/{id}")
    suspend fun getBandaById(@Path("id") id: Int): BandaDto
}