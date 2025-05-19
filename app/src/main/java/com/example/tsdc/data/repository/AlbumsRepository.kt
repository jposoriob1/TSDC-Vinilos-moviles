package com.example.tsdc.data.repository

import com.example.tsdc.data.model.AlbumDto
import com.example.tsdc.data.model.TrackDto
import com.example.tsdc.data.service.AlbumsService
import com.example.tsdc.data.service.TrackIdWrapper
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

    suspend fun getAllTracks(): List<TrackDto> {
        val cached = CacheManager.getInstance().getTracks()
        if (cached != null && cached.isNotEmpty()) {
            return cached
        }

        val fresh = albumsService.getAllTracks()
        CacheManager.getInstance().setTracks(fresh)
        return fresh
    }

    suspend fun updateAlbum(id: Int, album: AlbumDto): AlbumDto {
        val updated = albumsService.updateAlbum(id, album)
        CacheManager.getInstance().setAlbumById(id, updated)
        return updated
    }

    suspend fun associateTrackWithAlbum(albumId: Int, trackId: Int): AlbumDto {
        // Get the track details from the cache or API
        val allTracks = getAllTracks()
        val track = allTracks.find { it.id == trackId }
            ?: throw IllegalArgumentException("Track with ID $trackId not found")

        val trackIdWrapper = TrackIdWrapper(
            id = track.id,
            name = track.name,
            duration = track.duration
        )

        val updated = albumsService.associateTrackWithAlbum(albumId, trackIdWrapper)
        CacheManager.getInstance().setAlbumById(albumId, updated)
        return updated
    }

    suspend fun removeTrackFromAlbum(albumId: Int, trackId: Int): AlbumDto {
        val updated = albumsService.removeTrackFromAlbum(albumId, trackId)
        CacheManager.getInstance().setAlbumById(albumId, updated)
        return updated
    }
}
