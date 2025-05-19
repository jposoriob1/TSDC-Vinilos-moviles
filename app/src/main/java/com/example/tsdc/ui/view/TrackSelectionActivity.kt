package com.example.tsdc.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tsdc.data.repository.AlbumsRepository
import com.example.tsdc.data.service.AlbumsService
import com.example.tsdc.ui.theme.TSDCTheme
import com.example.tsdc.ui.viewmodel.TrackSelectionViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TrackSelectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val albumId = intent.getIntExtra("albumId", -1)
        if (albumId == -1) finish()

        setContent {
            TSDCTheme {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(AlbumsService::class.java)
                val repository = AlbumsRepository(service)
                val viewModel = TrackSelectionViewModel(repository)

                TrackSelectionScreen(
                    albumId = albumId,
                    viewModel = viewModel,
                    onBack = { finish() },
                    onAssociate = { finish() }
                )
            }
        }
    }
}