package com.example.tsdc.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsdc.data.model.AlbumDto
import com.example.tsdc.data.repository.AlbumsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlbumsViewModel(private val repository: AlbumsRepository) : ViewModel() {

    private val _albums = MutableStateFlow<List<AlbumDto>>(emptyList())
    val albums: StateFlow<List<AlbumDto>> = _albums


    fun fetchAlbums() {

        viewModelScope.launch {
            try {
                val result = repository.getAlbums()

                _albums.value = result
            } catch (e: Exception) {
                // pa manejar error
                println("Error: ${e.message}")
            }
        }
    }
}