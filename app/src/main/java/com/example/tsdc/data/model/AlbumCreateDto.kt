package com.example.tsdc.data.model

data class AlbumCreateDto(
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String
)