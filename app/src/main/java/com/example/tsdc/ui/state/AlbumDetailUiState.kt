package com.example.tsdc.ui.state

import com.example.tsdc.data.model.AlbumDto

sealed class AlbumDetailUiState {
    object Loading : AlbumDetailUiState()
    data class Success(val album: AlbumDto) : AlbumDetailUiState()
    data class Error(val message: String) : AlbumDetailUiState()
    object Empty : AlbumDetailUiState()
}
