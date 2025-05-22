package com.example.tsdc.data.local.dao

import androidx.room.*
import com.example.tsdc.data.local.entity.Album

@Dao
interface AlbumsDao {
    @Query("SELECT * FROM albums_table")
    suspend fun getAlbums(): List<Album>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<Album>)

    @Query("DELETE FROM albums_table")
    suspend fun clearAll()
}
