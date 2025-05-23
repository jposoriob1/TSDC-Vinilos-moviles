package com.example.tsdc.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.tsdc.data.model.AlbumCreateDto
import com.example.tsdc.data.model.GenreDto
import com.example.tsdc.data.model.RecordLabelDto
import com.example.tsdc.data.repository.AlbumsRepository
import com.example.tsdc.ui.state.AlbumCreationState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.tsdc.utils.CacheManager


class AlbumCreateViewModel(private val repository: AlbumsRepository) : ViewModel() {

    var creationState by mutableStateOf<AlbumCreationState>(AlbumCreationState.Idle)

    fun createAlbum(
        name: String,
        cover: String,
        releaseDate: String,
        description: String,
        genre: GenreDto,
        recordLabel: RecordLabelDto
    ) {
        if (name.isBlank() || cover.isBlank() || releaseDate.isBlank() || description.isBlank()) {
            creationState = AlbumCreationState.Error("Todos los campos son obligatorios.")
            return
        }

        viewModelScope.launch {
            creationState = AlbumCreationState.Loading
            try {
                repository.createAlbum(
                    AlbumCreateDto(
                        name = name,
                        cover = cover,
                        releaseDate = releaseDate,
                        description = description,
                        genre = genre.value,
                        recordLabel = recordLabel.value
                    )
                )

                CacheManager.getInstance().setAlbums(emptyList())

                repository.refreshAlbums()

                creationState = AlbumCreationState.Success
            } catch (e: Exception) {
                creationState = AlbumCreationState.Error("Error al crear álbum: ${e.message}")
            }
        }
    }
}
