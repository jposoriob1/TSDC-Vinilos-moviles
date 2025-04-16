package com.example.tsdc.data.repository

import com.example.tsdc.data.model.AlbumDto
import com.example.tsdc.data.service.AlbumsService

class AlbumsRepository(private val albumsService: AlbumsService) {

    suspend fun getAlbums(): List<AlbumDto> {
        return albumsService.getAlbums()
    }
}