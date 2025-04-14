package com.example.tsdc.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tsdc.R
import com.example.tsdc.ui.theme.TSDCTheme
import com.example.tsdc.data.repository.AlbumsRepository
import com.example.tsdc.data.service.AlbumsService
import com.example.tsdc.ui.viewmodel.AlbumsViewModel
import com.example.tsdc.ui.view.AlbumesScreen
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AlbumesActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TSDCTheme {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://host.docker.internal:3000/")

                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(AlbumsService::class.java)
                val repository = AlbumsRepository(service)
                val viewModel = AlbumsViewModel(repository)

                AlbumesScreen(viewModel = viewModel)
                println("Retrofit y ViewModel inicializados")
            }
        }
    }
}