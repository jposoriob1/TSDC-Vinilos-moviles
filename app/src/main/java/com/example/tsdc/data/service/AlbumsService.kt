package com.example.tsdc.data.service

import com.example.tsdc.data.model.AlbumDto
import retrofit2.http.GET

interface AlbumsService {
    @GET("albums")
    suspend fun getAlbums(): List<AlbumDto>
}