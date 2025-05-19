package com.example.tsdc.data.service

import com.example.tsdc.data.model.AlbumDto
import com.example.tsdc.data.model.TrackDto
import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// Data class to wrap the track data for the request body
data class TrackIdWrapper(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("duration")
    val duration: String
)

interface AlbumsService {
    @GET("/albums")
    suspend fun getAlbums(): List<AlbumDto>

    @GET("/albums/{id}")
    suspend fun getAlbumById(@Path("id") id: Int): AlbumDto

    @GET("/tracks")
    suspend fun getAllTracks(): List<TrackDto>

    @PUT("/albums/{id}")
    suspend fun updateAlbum(@Path("id") id: Int, @Body album: AlbumDto): AlbumDto

    @POST("/albums/{albumId}/tracks")
    suspend fun associateTrackWithAlbum(@Path("albumId") albumId: Int, @Body trackIdWrapper: TrackIdWrapper): AlbumDto

    @DELETE("/albums/{albumId}/tracks/{trackId}")
    suspend fun removeTrackFromAlbum(@Path("albumId") albumId: Int, @Path("trackId") trackId: Int): AlbumDto
}
