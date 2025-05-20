package com.example.tsdc.data.repository

import com.example.tsdc.data.model.AlbumCreateDto
import com.example.tsdc.data.model.AlbumDto
import com.example.tsdc.data.service.AlbumsService
import com.example.tsdc.utils.CacheManager

class AlbumsRepository(private val albumsService: AlbumsService) {

    suspend fun getAlbums(): List<AlbumDto> {
        val cached = CacheManager.getInstance().getAlbums()
        if (cached != null && cached.isNotEmpty()) {
            return cached
        }

        val fresh = albumsService.getAlbums()
        CacheManager.getInstance().setAlbums(fresh)
        return fresh
    }

    suspend fun getAlbumById(id: Int): AlbumDto {
        val cached = CacheManager.getInstance().getAlbumById(id)
        if (cached != null) {
            return cached
        }

        val fresh = albumsService.getAlbumById(id)
        CacheManager.getInstance().setAlbumById(id, fresh)
        return fresh
    }

    suspend fun createAlbum(album: AlbumCreateDto) {
        albumsService.createAlbum(album)
    }
}
