package com.example.tsdc.data.model

sealed class ArtistaDto {
    abstract val id: Int
    abstract val name: String
    abstract val image: String
    abstract val description: String
}