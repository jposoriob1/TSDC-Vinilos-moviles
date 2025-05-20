package com.example.tsdc.data.service

import com.example.tsdc.data.model.AlbumCreateDto
import com.example.tsdc.data.model.AlbumDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AlbumsService {
    @GET("albums")
    suspend fun getAlbums(): List<AlbumDto>

    @GET("albums/{id}")
    suspend fun getAlbumById(@Path("id") id: Int): AlbumDto

    @POST("albums")
    suspend fun createAlbum(@Body album: AlbumCreateDto)
}