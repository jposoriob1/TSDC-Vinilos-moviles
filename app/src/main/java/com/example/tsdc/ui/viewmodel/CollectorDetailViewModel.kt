package com.example.tsdc.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsdc.data.repository.CollectorsRepository
import com.example.tsdc.ui.state.CollectorDetailUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorDetailViewModel(private val repository: CollectorsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<CollectorDetailUiState>(CollectorDetailUiState.Loading)
    val uiState: StateFlow<CollectorDetailUiState> = _uiState

    fun fetchCollectorById(id: Int) {
        _uiState.value = CollectorDetailUiState.Loading

        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    repository.getCollectorById(id)
                }

                _uiState.value = if (result != null) {
                    CollectorDetailUiState.Success(result)
                } else {
                    CollectorDetailUiState.Empty
                }
            } catch (e: Exception) {
                _uiState.value = CollectorDetailUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
