package com.example.tsdc.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tsdc.data.model.TipoArtistaDto
import com.example.tsdc.ui.theme.TSDCTheme
import com.example.tsdc.data.repository.ArtistasRepository
import com.example.tsdc.data.service.ArtistasService
import com.example.tsdc.ui.viewmodel.ArtistasViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArtistasActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TSDCTheme {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(ArtistasService::class.java)
                val repository = ArtistasRepository(service)
                val viewModel = ArtistasViewModel(repository)

                ArtistasScreen(
                    viewModel = viewModel,
                    onBack = { finish() },
                    onItemClick = { artistaId ->
                        val tipoArtista = viewModel.tipoSeleccionado.value ?: TipoArtistaDto.MUSICO
                        val intent = Intent(this, ArtistaDetailActivity::class.java)
                        intent.putExtra("artistaId", artistaId)
                        intent.putExtra("tipoArtista", tipoArtista.name)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}
