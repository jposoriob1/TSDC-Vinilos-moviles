package com.example.tsdc.data.service

import com.example.tsdc.data.model.CollectorDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CollectorsService {
    @GET("collectors")
    suspend fun getCollectors(): List<CollectorDto>

    @GET("collectors/{id}")
    suspend fun getCollectorById(@Path("id") id: Int): CollectorDto
}