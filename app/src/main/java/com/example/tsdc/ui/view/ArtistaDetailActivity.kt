package com.example.tsdc.ui.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import com.example.tsdc.data.local.VinylRoomDatabase
import com.example.tsdc.ui.theme.TSDCTheme
import com.example.tsdc.data.model.TipoArtistaDto
import com.example.tsdc.data.repository.ArtistasRepository
import com.example.tsdc.data.service.ArtistasService
import com.example.tsdc.ui.viewmodel.ArtistaDetailViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArtistaDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val artistaId = intent.getIntExtra("artistaId", -1)
        if (artistaId == -1) {
            finish()
            return
        }

        val tipoArtistaStr = intent.getStringExtra("tipoArtista") ?: "MUSICO"
        val tipoArtista = TipoArtistaDto.valueOf(tipoArtistaStr)
        Log.d("ArtistaDetail", "Recibido artistaId: $artistaId, tipo: $tipoArtistaStr")

        // Crear instancia de Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Obtener DAOs y crear repositorio
        val database = VinylRoomDatabase.getDatabase(applicationContext)
        val musicosDao = database.musicosDao()
        val bandasDao = database.bandasDao()
        val service = retrofit.create(ArtistasService::class.java)
        val repository = ArtistasRepository(service, musicosDao, bandasDao)

        setContent {
            TSDCTheme {
                val viewModel = remember { ArtistaDetailViewModel(repository) }

                ArtistaDetailScreen(
                    artistaId = artistaId,
                    tipo = tipoArtista,
                    viewModel = viewModel,
                    onBack = { finish() }
                )
            }
        }
    }
}
