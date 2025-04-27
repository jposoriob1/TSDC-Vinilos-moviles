package com.example.tsdc.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tsdc.R
import com.example.tsdc.ui.theme.TSDCTheme
import com.example.tsdc.data.repository.AlbumsRepository
import com.example.tsdc.data.service.AlbumsService
import com.example.tsdc.ui.viewmodel.AlbumsViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AlbumesActivity : ComponentActivity() {
    @OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TSDCTheme {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(AlbumsService::class.java)
                val repository = AlbumsRepository(service)
                val viewModel = AlbumsViewModel(repository)

                AlbumesScreen(
                    viewModel = viewModel,
                    onBack = { finish() },
                    onAlbumClick = { albumId ->
                        val intent = Intent(this, AlbumDetailActivity::class.java)
                        intent.putExtra("albumId", albumId)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}