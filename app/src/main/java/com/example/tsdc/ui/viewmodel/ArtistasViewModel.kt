package com.example.tsdc.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsdc.data.model.ArtistaDto
import com.example.tsdc.data.model.TipoArtistaDto
import com.example.tsdc.data.repository.ArtistasRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArtistasViewModel(private val repository: ArtistasRepository) : ViewModel() {

    private val _artistas = MutableLiveData<List<ArtistaDto>>()
    val artistas: LiveData<List<ArtistaDto>> = _artistas

    private val _tipoSeleccionado = MutableLiveData(TipoArtistaDto.MUSICO)
    val tipoSeleccionado: LiveData<TipoArtistaDto> = _tipoSeleccionado

    init {
        cargarArtistas()
    }

    fun cambiarTipo(tipo: TipoArtistaDto) {
        _tipoSeleccionado.value = tipo
        cargarArtistas()
    }

    private fun cargarArtistas() {
        viewModelScope.launch {
            try {
                val resultado = withContext(Dispatchers.IO) {
                    when (_tipoSeleccionado.value) {
                        TipoArtistaDto.MUSICO -> repository.getMusicos()
                        TipoArtistaDto.BANDA -> repository.getBandas()
                        else -> emptyList()
                    }
                }
                _artistas.value = resultado
            } catch (e: Exception) {
                _artistas.value = emptyList()
            }
        }
    }
}


