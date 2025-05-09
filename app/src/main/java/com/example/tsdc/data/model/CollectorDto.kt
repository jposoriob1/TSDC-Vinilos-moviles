package com.example.tsdc.data.model

data class CollectorDto(
    val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val comments: List<CommentDto>,
    val favoritePerformers: List<PerformerDto>,
    val collectorAlbums: List<CollectorAlbumDto>
)

data class CollectorAlbumDto(
    val id: Int,
    val price: Int,
    val status: String
)