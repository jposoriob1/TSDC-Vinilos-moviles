package com.example.tsdc.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import com.example.tsdc.ui.theme.TSDCTheme
import com.example.tsdc.data.repository.AlbumsRepository
import com.example.tsdc.data.service.AlbumsService
import com.example.tsdc.ui.viewmodel.AlbumsViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.lifecycle.ViewModelProvider


class AlbumesActivity : ComponentActivity() {


    private lateinit var viewModel: AlbumsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(AlbumsService::class.java)
        val repository = AlbumsRepository(
            albumsService = service,
            albumsDao = (application as com.example.tsdc.VinylsApplication).database.albumsDao()
        )

        viewModel = ViewModelProvider(
            this,
            AlbumsViewModel.provideFactory(repository)
        )[AlbumsViewModel::class.java]

        setContent {
            TSDCTheme {

                LaunchedEffect(Unit) {
                    viewModel.fetchAlbums()
                }

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


    override fun onResume() {
        super.onResume()
        viewModel.fetchAlbums()
    }
}
