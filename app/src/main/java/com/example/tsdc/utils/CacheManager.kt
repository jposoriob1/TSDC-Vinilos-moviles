package com.example.tsdc.utils

import android.util.SparseArray
import com.example.tsdc.data.model.AlbumDto
import com.example.tsdc.data.model.ArtistaDto
import com.example.tsdc.data.model.CollectorDto

class CacheManager private constructor() {

    companion object {
        private var instance: CacheManager? = null

        fun getInstance(): CacheManager {
            if (instance == null) {
                instance = CacheManager()
            }
            return instance!!
        }
    }

    // Cache para listas
    private var albumsListCache: List<AlbumDto>? = null
    private var musicosListCache: List<ArtistaDto>? = null
    private var bandasListCache: List<ArtistaDto>? = null
    private var collectorsListCache: List<CollectorDto>? = null

    // Cache individuales por ID
    private val albumDetailCache = SparseArray<AlbumDto>()
    private val musicoDetailCache = SparseArray<ArtistaDto>()
    private val bandaDetailCache = SparseArray<ArtistaDto>()
    private val collectorDetailCache = SparseArray<CollectorDto>()

    // === Álbumes ===
    fun getAlbums(): List<AlbumDto>? = albumsListCache
    fun setAlbums(albums: List<AlbumDto>) {
        albumsListCache = albums
    }

    fun getAlbumById(id: Int): AlbumDto? = albumDetailCache[id]
    fun setAlbumById(id: Int, album: AlbumDto) {
        albumDetailCache.put(id, album)
    }

    // === Músicos ===
    fun getMusicos(): List<ArtistaDto>? = musicosListCache
    fun setMusicos(musicos: List<ArtistaDto>) {
        musicosListCache = musicos
    }

    fun getMusicoById(id: Int): ArtistaDto? = musicoDetailCache[id]
    fun setMusicoById(id: Int, musico: ArtistaDto) {
        musicoDetailCache.put(id, musico)
    }

    // === Bandas ===
    fun getBandas(): List<ArtistaDto>? = bandasListCache
    fun setBandas(bandas: List<ArtistaDto>) {
        bandasListCache = bandas
    }

    fun getBandaById(id: Int): ArtistaDto? = bandaDetailCache[id]
    fun setBandaById(id: Int, banda: ArtistaDto) {
        bandaDetailCache.put(id, banda)
    }

    // === Coleccionistas ===
    fun getCollectors(): List<CollectorDto>? = collectorsListCache
    fun setCollectors(collectors: List<CollectorDto>) {
        collectorsListCache = collectors
    }

    fun getCollectorById(id: Int): CollectorDto? = collectorDetailCache[id]
    fun setCollectorById(id: Int, collector: CollectorDto) {
        collectorDetailCache.put(id, collector)
    }
}
