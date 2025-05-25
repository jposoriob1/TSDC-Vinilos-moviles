package com.example.tsdc.data.repository

import com.example.tsdc.data.local.dao.AlbumsDao
import com.example.tsdc.data.local.entity.Album
import com.example.tsdc.data.model.AlbumCreateDto
import com.example.tsdc.data.model.AlbumDto
import com.example.tsdc.data.model.TrackCreateDto
import com.example.tsdc.data.model.TrackDto
import com.example.tsdc.data.service.AlbumsService
import com.example.tsdc.utils.CacheManager

class AlbumsRepository(
    private val albumsService: AlbumsService,
    private val albumsDao: AlbumsDao
) {

    suspend fun getAlbums(): List<AlbumDto> {
        val cache = CacheManager.getInstance().getAlbums()
        if (cache != null && cache.isNotEmpty()) {
            return cache
        }

        // Intenta desde Room
        val localAlbums = albumsDao.getAlbums()
        if (localAlbums.isNotEmpty()) {
            val albumsDto = localAlbums.map {
                AlbumDto(
                    id = it.id,
                    name = it.name,
                    cover = it.cover,
                    releaseDate = it.releaseDate,
                    description = it.description,
                    genre = it.genre,
                    recordLabel = it.recordLabel,
                    tracks = emptyList(),
                    performers = emptyList(),
                    comments = emptyList()
                )
            }

            CacheManager.getInstance().setAlbums(albumsDto)
            return albumsDto
        }

        // Desde red
        val fresh = albumsService.getAlbums()
        CacheManager.getInstance().setAlbums(fresh)

        // Guarda en Room (solo los campos que se pueden guardar)
        val localToSave = fresh.map {
            Album(
                id = it.id,
                name = it.name,
                cover = it.cover,
                releaseDate = it.releaseDate,
                description = it.description,
                genre = it.genre,
                recordLabel = it.recordLabel
            )
        }

        albumsDao.insertAll(localToSave)

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

    suspend fun createTrack(albumId: Int, track: TrackCreateDto): TrackDto {
        val result = albumsService.createTrack(albumId, track)

        // Refresh the album in cache to ensure we have the updated data with the new track
        val updatedAlbum = albumsService.getAlbumById(albumId)
        CacheManager.getInstance().setAlbumById(albumId, updatedAlbum)

        return result
    }

    suspend fun refreshAlbums() {
        val freshAlbums = albumsService.getAlbums()


        albumsDao.clearAll()


        val localToSave = freshAlbums.map {
            Album(
                id = it.id,
                name = it.name,
                cover = it.cover,
                releaseDate = it.releaseDate,
                description = it.description,
                genre = it.genre,
                recordLabel = it.recordLabel
            )
        }


        albumsDao.insertAll(localToSave)
    }

}
