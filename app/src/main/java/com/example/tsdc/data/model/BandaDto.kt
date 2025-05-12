package com.example.tsdc.data.model

data class BandaDto(
    override val id: Int,
    override val name: String,
    override val image: String,
    override val description: String,
    val creationDate: String,
    val albums: List<AlbumDto>,
    val musicians: List<MusicoDto>, // si necesitas cargar esta parte
    val performerPrizes: List<PremioDto>
) : ArtistaDto()