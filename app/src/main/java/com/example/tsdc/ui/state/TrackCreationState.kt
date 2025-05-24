package com.example.tsdc.ui.state

sealed class TrackCreationState {
    object Initial : TrackCreationState()
    object Loading : TrackCreationState()
    object Success : TrackCreationState()
    data class Error(val message: String) : TrackCreationState()
}