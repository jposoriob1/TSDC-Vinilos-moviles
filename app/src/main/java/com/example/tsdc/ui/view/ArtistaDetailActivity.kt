package com.example.tsdc.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.example.tsdc.ui.theme.TSDCTheme
import com.example.tsdc.data.model.TipoArtistaDto

class ArtistaDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val artistaId = intent.getIntExtra("artistaId", -1)
        val tipoArtistaStr = intent.getStringExtra("tipoArtista") ?: "MUSICO"
        val tipoArtista = TipoArtistaDto.valueOf(tipoArtistaStr)

        setContent {
            TSDCTheme {
                // Aca debe ir la logica del endpoint revisa el AlbumDetailActivity
                // ojo son dos diferente porque uno es para musicos y otro para bandas


                when (tipoArtista) {
                    TipoArtistaDto.MUSICO -> {
                        Text(text = "Detalle del MÚSICO con ID: $artistaId")
                        // y aca llamas la logica de como llamar al endpoint /musicians/$artistaId
                        // recuerda que debes montar el viewmodel el servicio y el repositorio ya estan listos
                    }

                    TipoArtistaDto.BANDA -> {
                        Text(text = "Detalle de la BANDA con ID: $artistaId")
                        //  aca llamas la logica llamar al endpoint /bands/$artistaId
                        // recuerda que debes montar el viewmodel el servicio y el repositorio ya estan listos
                    }
                }
            }
        }
    }
}