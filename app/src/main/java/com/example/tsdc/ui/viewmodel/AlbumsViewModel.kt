package com.example.tsdc.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsdc.data.repository.AlbumsRepository
import com.example.tsdc.ui.state.AlbumsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.ViewModelProvider


class AlbumsViewModel(private val repository: AlbumsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<AlbumsUiState>(AlbumsUiState.Loading)
    val uiState: StateFlow<AlbumsUiState> = _uiState

    fun fetchAlbums() {
        _uiState.value = AlbumsUiState.Loading

        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    repository.getAlbums()
                }

                _uiState.value = if (result.isEmpty()) {
                    AlbumsUiState.Empty
                } else {
                    AlbumsUiState.Success(result)
                }
            } catch (e: Exception) {
                _uiState.value = AlbumsUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
    companion object {
        fun provideFactory(repository: AlbumsRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return AlbumsViewModel(repository) as T
                }
            }
    }
}

