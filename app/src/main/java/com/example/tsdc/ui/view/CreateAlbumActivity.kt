package com.example.tsdc.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tsdc.data.repository.AlbumsRepository
import com.example.tsdc.data.service.AlbumsService
import com.example.tsdc.ui.theme.TSDCTheme
import com.example.tsdc.ui.viewmodel.AlbumCreateViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CreateAlbumActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Crea Retrofit y el servicio
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(AlbumsService::class.java)
        val repository = AlbumsRepository(service)

        // Crea el ViewModel
        val viewModel = AlbumCreateViewModel(repository)

        setContent {
            TSDCTheme {
                CreateAlbumScreen(
                    viewModel = viewModel,
                    onBack = { finish() }
                )
            }
        }
    }

}
