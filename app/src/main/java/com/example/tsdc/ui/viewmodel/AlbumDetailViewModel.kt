package com.example.tsdc.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsdc.data.model.AlbumDto
import com.example.tsdc.data.repository.AlbumsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlbumDetailViewModel(private val repository: AlbumsRepository) : ViewModel() {

    private val _album = MutableStateFlow<AlbumDto?>(null)
    val album: StateFlow<AlbumDto?> = _album

    fun fetchAlbumById(id: Int) {
        viewModelScope.launch {
            try {
                _album.value = repository.getAlbumById(id)
            } catch (e: Exception) {
                println("Error al obtener álbum: ${e.message}")
            }
        }
    }
}