package com.example.tsdc.ui.state

import com.example.tsdc.data.model.CollectorDto

sealed class CollectorDetailUiState {
    object Loading : CollectorDetailUiState()
    data class Success(val collector: CollectorDto) : CollectorDetailUiState()
    data class Error(val message: String) : CollectorDetailUiState()
    object Empty : CollectorDetailUiState()
}
