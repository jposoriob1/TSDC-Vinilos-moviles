package com.example.tsdc.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsdc.data.model.ArtistaDto
import com.example.tsdc.data.model.TipoArtistaDto
import com.example.tsdc.data.repository.ArtistasRepository
import kotlinx.coroutines.launch

class ArtistasViewModel(private val repository: ArtistasRepository) : ViewModel() {



    private val _artistas = MutableLiveData<List<ArtistaDto>>()
    val artistas: LiveData<List<ArtistaDto>> = _artistas

    private val _tipoSeleccionado = MutableLiveData(TipoArtistaDto.MUSICO)
    val tipoSeleccionado: LiveData<TipoArtistaDto> = _tipoSeleccionado

    init {
        cargarArtistas() // carga inicial
    }

    fun cambiarTipo(tipo: TipoArtistaDto) {
        _tipoSeleccionado.value = tipo
        cargarArtistas()
    }

    private fun cargarArtistas() {
        viewModelScope.launch {
            try {
                _artistas.value = when (_tipoSeleccionado.value) {
                    TipoArtistaDto.MUSICO -> repository.getMusicos()
                    TipoArtistaDto.BANDA -> repository.getBandas()
                    else -> emptyList()
                }
            } catch (e: Exception) {
                // puedes loggear o manejar el error aquí si lo deseas
                _artistas.value = emptyList()
            }
        }
    }
}