package com.example.tsdc.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsdc.data.model.TrackCreateDto
import com.example.tsdc.data.repository.AlbumsRepository
import com.example.tsdc.ui.state.AlbumDetailUiState
import com.example.tsdc.ui.state.TrackCreationState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumDetailViewModel(private val repository: AlbumsRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<AlbumDetailUiState>(AlbumDetailUiState.Loading)
    val uiState: StateFlow<AlbumDetailUiState> = _uiState

    private val _trackCreationState = MutableStateFlow<TrackCreationState>(TrackCreationState.Initial)
    val trackCreationState: StateFlow<TrackCreationState> = _trackCreationState

    fun fetchAlbumById(id: Int) {
        _uiState.value = AlbumDetailUiState.Loading

        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    repository.getAlbumById(id)
                }

                _uiState.value = if (result != null) {
                    AlbumDetailUiState.Success(result)
                } else {
                    AlbumDetailUiState.Empty
                }
            } catch (e: Exception) {
                _uiState.value = AlbumDetailUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun createTrack(albumId: Int, name: String, duration: String) {
        _trackCreationState.value = TrackCreationState.Loading

        viewModelScope.launch {
            try {
                val track = TrackCreateDto(name = name, duration = duration)

                withContext(Dispatchers.IO) {
                    repository.createTrack(albumId, track)
                }

                _trackCreationState.value = TrackCreationState.Success

                // Refresh the album data to show the new track
                fetchAlbumById(albumId)
            } catch (e: Exception) {
                _trackCreationState.value = TrackCreationState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun resetTrackCreationState() {
        _trackCreationState.value = TrackCreationState.Initial
    }
}
