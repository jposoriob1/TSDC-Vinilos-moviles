package com.example.tsdc.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsdc.data.model.AlbumDto
import com.example.tsdc.data.model.TrackDto
import com.example.tsdc.data.repository.AlbumsRepository
import com.example.tsdc.ui.state.TrackSelectionUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrackSelectionViewModel(private val repository: AlbumsRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<TrackSelectionUiState>(TrackSelectionUiState.Loading)
    val uiState: StateFlow<TrackSelectionUiState> = _uiState

    fun loadTracksForAlbum(albumId: Int) {
        _uiState.value = TrackSelectionUiState.Loading

        viewModelScope.launch {
            try {
                val album = withContext(Dispatchers.IO) {
                    repository.getAlbumById(albumId)
                }

                val allTracks = withContext(Dispatchers.IO) {
                    repository.getAllTracks()
                }

                if (album != null && allTracks.isNotEmpty()) {
                    _uiState.value = TrackSelectionUiState.Success(
                        allTracks = allTracks,
                        selectedTracks = album.tracks,
                        album = album
                    )
                } else {
                    _uiState.value = TrackSelectionUiState.Empty
                }
            } catch (e: Exception) {
                _uiState.value = TrackSelectionUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun updateFilterText(filterText: String) {
        val currentState = _uiState.value
        if (currentState is TrackSelectionUiState.Success) {
            _uiState.value = currentState.copy(filterText = filterText)
        }
    }

    fun toggleTrackSelection(track: TrackDto) {
        val currentState = _uiState.value
        if (currentState is TrackSelectionUiState.Success) {
            val selectedTracks = currentState.selectedTracks.toMutableList()

            if (selectedTracks.any { it.id == track.id }) {
                selectedTracks.removeAll { it.id == track.id }
            } else {
                selectedTracks.add(track)
            }

            _uiState.value = currentState.copy(selectedTracks = selectedTracks)
        }
    }

    fun associateTracksWithAlbum() {
        val currentState = _uiState.value
        if (currentState is TrackSelectionUiState.Success) {
            val album = currentState.album
            val selectedTracks = currentState.selectedTracks

            viewModelScope.launch {
                try {
                    _uiState.value = TrackSelectionUiState.Loading

                    var updatedAlbum = album

                    // Find tracks that need to be associated (selected but not already in the album)
                    val tracksToAssociate = selectedTracks.filter { selectedTrack ->
                        !album.tracks.any { it.id == selectedTrack.id }
                    }

                    // Find tracks that need to be removed (in album but not selected)
                    val tracksToRemove = album.tracks.filter { albumTrack ->
                        !selectedTracks.any { it.id == albumTrack.id }
                    }

                    // Associate each track with the album
                    for (track in tracksToAssociate) {
                        updatedAlbum = withContext(Dispatchers.IO) {
                            repository.associateTrackWithAlbum(album.id, track.id)
                        }
                    }

                    // Remove each track from the album
                    for (track in tracksToRemove) {
                        updatedAlbum = withContext(Dispatchers.IO) {
                            repository.removeTrackFromAlbum(album.id, track.id)
                        }
                    }

                    // If no tracks were associated or removed, just use the current album
                    if (tracksToAssociate.isEmpty() && tracksToRemove.isEmpty()) {
                        updatedAlbum = album
                    }

                    _uiState.value = TrackSelectionUiState.Success(
                        allTracks = currentState.allTracks,
                        selectedTracks = updatedAlbum.tracks,
                        filterText = currentState.filterText,
                        album = updatedAlbum
                    )
                } catch (e: Exception) {
                    _uiState.value = TrackSelectionUiState.Error(e.message ?: "Error al asociar canciones")
                }
            }
        }
    }

    fun isTrackSelected(track: TrackDto): Boolean {
        val currentState = _uiState.value
        return if (currentState is TrackSelectionUiState.Success) {
            currentState.selectedTracks.any { it.id == track.id }
        } else {
            false
        }
    }
}
