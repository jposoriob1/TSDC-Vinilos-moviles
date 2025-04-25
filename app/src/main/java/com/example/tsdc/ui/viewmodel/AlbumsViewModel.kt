package com.example.tsdc.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsdc.data.model.AlbumDto
import com.example.tsdc.data.repository.AlbumsRepository
import com.example.tsdc.ui.state.AlbumsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlbumsViewModel(private val repository: AlbumsRepository) : ViewModel() {

    private val _uiState =MutableStateFlow<AlbumsUiState>(AlbumsUiState.Loading)
    val uiState: StateFlow<AlbumsUiState> = _uiState


    fun fetchAlbums() {
        _uiState.value = AlbumsUiState.Loading

        viewModelScope.launch {
            try {
                val result = repository.getAlbums()
                if (result.isEmpty()) {
                    _uiState.value = AlbumsUiState.Empty
                } else {
                    _uiState.value = AlbumsUiState.Success(result)
                }
            } catch (e: Exception) {
                _uiState.value = AlbumsUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}