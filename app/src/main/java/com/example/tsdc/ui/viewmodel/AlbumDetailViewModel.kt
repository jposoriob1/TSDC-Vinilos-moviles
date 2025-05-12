package com.example.tsdc.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsdc.data.repository.AlbumsRepository
import com.example.tsdc.ui.state.AlbumDetailUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumDetailViewModel(private val repository: AlbumsRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<AlbumDetailUiState>(AlbumDetailUiState.Loading)
    val uiState: StateFlow<AlbumDetailUiState> = _uiState

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
}
