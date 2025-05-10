package com.example.tsdc.ui.state

import com.example.tsdc.data.model.BandaDto
import com.example.tsdc.data.model.MusicoDto

sealed class ArtistaDetailUiState {
    object Loading : ArtistaDetailUiState()
    data class SuccessMusico(val musico: MusicoDto) : ArtistaDetailUiState()
    data class SuccessBanda(val banda: BandaDto) : ArtistaDetailUiState()
    data class Error(val message: String) : ArtistaDetailUiState()
}