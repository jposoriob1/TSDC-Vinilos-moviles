package com.example.tsdc.ui.state

import com.example.tsdc.data.model.CollectorDto

sealed class CollectorsUiState {
    object Loading : CollectorsUiState()
    data class Success(val collectors: List<CollectorDto>) : CollectorsUiState()
    data class Error(val message: String) : CollectorsUiState()
    object Empty : CollectorsUiState()
}