package com.example.tsdc.ui.state


sealed class AlbumCreationState {
    object Idle : AlbumCreationState()
    object Loading : AlbumCreationState()
    object Success : AlbumCreationState()
    data class Error(val message: String) : AlbumCreationState()
}