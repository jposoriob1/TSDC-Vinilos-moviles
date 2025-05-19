package com.example.tsdc.ui.state

import com.example.tsdc.data.model.AlbumDto
import com.example.tsdc.data.model.TrackDto

sealed class TrackSelectionUiState {
    object Loading : TrackSelectionUiState()
    data class Success(
        val allTracks: List<TrackDto>,
        val selectedTracks: List<TrackDto>,
        val filterText: String = "",
        val album: AlbumDto
    ) : TrackSelectionUiState()
    data class Error(val message: String) : TrackSelectionUiState()
    object Empty : TrackSelectionUiState()
}