package com.example.tsdc.data.local.dao

import androidx.room.*
import com.example.tsdc.data.local.entity.Musico

@Dao
interface MusicosDao {
    @Query("SELECT * FROM musicos_table")
    suspend fun getMusicos(): List<Musico>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(musicos: List<Musico>)

    @Query("DELETE FROM musicos_table")
    suspend fun clearAll()
}
