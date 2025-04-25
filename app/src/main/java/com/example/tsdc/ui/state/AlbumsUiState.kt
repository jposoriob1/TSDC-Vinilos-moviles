package com.example.tsdc.ui.state

import com.example.tsdc.data.model.AlbumDto

sealed class AlbumsUiState {
    object Loading : AlbumsUiState()
    data class Success(val albums: List<AlbumDto>) : AlbumsUiState()
    data class Error(val message: String) : AlbumsUiState()
    object Empty : AlbumsUiState()
}