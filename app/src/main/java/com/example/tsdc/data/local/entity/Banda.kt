package com.example.tsdc.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bandas_table")
data class Banda(
    @PrimaryKey val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val creationDate: String
)
