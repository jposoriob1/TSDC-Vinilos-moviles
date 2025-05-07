package com.example.tsdc.data.model

data class MusicoDto(
    override val id: Int,
    override val name: String,
    override val image: String,
    override val description: String,
    val birthDate: String,
    val albums: List<AlbumDto>,
    val performerPrizes: List<PremioDto>
) : ArtistaDto()