package com.example.tsdc.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsdc.data.repository.CollectorsRepository
import com.example.tsdc.ui.state.CollectorsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorsViewModel(private val repository: CollectorsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<CollectorsUiState>(CollectorsUiState.Loading)
    val uiState: StateFlow<CollectorsUiState> = _uiState

    fun fetchCollectors() {
        _uiState.value = CollectorsUiState.Loading

        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    repository.getCollectors()
                }

                _uiState.value = if (result.isEmpty()) {
                    CollectorsUiState.Empty
                } else {
                    CollectorsUiState.Success(result)
                }
            } catch (e: Exception) {
                _uiState.value = CollectorsUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
