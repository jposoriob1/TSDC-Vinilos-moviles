package com.example.tsdc.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsdc.data.repository.ArtistasRepository
import com.example.tsdc.data.model.TipoArtistaDto
import com.example.tsdc.ui.state.ArtistaDetailUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistaDetailViewModel(
    private val repository: ArtistasRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ArtistaDetailUiState>(ArtistaDetailUiState.Loading)
    val uiState: StateFlow<ArtistaDetailUiState> = _uiState

    fun cargarDetalle(artistaId: Int, tipo: TipoArtistaDto) {
        viewModelScope.launch {
            _uiState.value = ArtistaDetailUiState.Loading
            try {
                val resultado = withContext(Dispatchers.IO) {
                    when (tipo) {
                        TipoArtistaDto.MUSICO -> {
                            val musico = repository.getMusicoById(artistaId)
                            ArtistaDetailUiState.SuccessMusico(musico)
                        }

                        TipoArtistaDto.BANDA -> {
                            val banda = repository.getBandaById(artistaId)
                            ArtistaDetailUiState.SuccessBanda(banda)
                        }
                    }
                }
                _uiState.value = resultado
            } catch (e: Exception) {
                _uiState.value = ArtistaDetailUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
