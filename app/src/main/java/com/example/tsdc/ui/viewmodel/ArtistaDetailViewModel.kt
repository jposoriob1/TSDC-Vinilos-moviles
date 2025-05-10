package com.example.tsdc.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsdc.data.repository.ArtistasRepository
import com.example.tsdc.data.model.TipoArtistaDto
import com.example.tsdc.ui.state.ArtistaDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArtistaDetailViewModel(
    private val repository: ArtistasRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ArtistaDetailUiState>(ArtistaDetailUiState.Loading)
    val uiState: StateFlow<ArtistaDetailUiState> = _uiState

    fun cargarDetalle(artistaId: Int, tipo: TipoArtistaDto) {
        viewModelScope.launch {
            try {
                _uiState.value = ArtistaDetailUiState.Loading
                _uiState.value = when (tipo) {
                    TipoArtistaDto.MUSICO -> {
                        val musico = repository.getMusicoById(artistaId)
                        ArtistaDetailUiState.SuccessMusico(musico)
                    }

                    TipoArtistaDto.BANDA -> {
                        val banda = repository.getBandaById(artistaId)
                        ArtistaDetailUiState.SuccessBanda(banda)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = ArtistaDetailUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
