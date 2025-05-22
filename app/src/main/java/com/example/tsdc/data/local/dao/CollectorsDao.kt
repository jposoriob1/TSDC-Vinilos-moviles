package com.example.tsdc.data.local.dao

import androidx.room.*
import com.example.tsdc.data.local.entity.Collector

@Dao
interface CollectorsDao {
    @Query("SELECT * FROM collectors_table")
    suspend fun getCollectors(): List<Collector>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(collectors: List<Collector>)

    @Query("DELETE FROM collectors_table")
    suspend fun clearAll()
}
