package com.example.tsdc.data.model

data class AlbumDto (
    val id: Int,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String,
    val tracks: List<TrackDto>,
    val performers: List<PerformerDto>,
    val comments: List<CommentDto>
)