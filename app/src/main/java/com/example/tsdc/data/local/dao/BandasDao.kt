package com.example.tsdc.data.local.dao

import androidx.room.*
import com.example.tsdc.data.local.entity.Banda

@Dao
interface BandasDao {
    @Query("SELECT * FROM bandas_table")
    suspend fun getBandas(): List<Banda>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(bandas: List<Banda>)

    @Query("DELETE FROM bandas_table")
    suspend fun clearAll()
}
