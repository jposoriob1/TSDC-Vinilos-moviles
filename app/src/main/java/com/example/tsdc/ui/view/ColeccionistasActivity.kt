package com.example.tsdc.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tsdc.ui.theme.TSDCTheme
import com.example.tsdc.data.repository.CollectorsRepository
import com.example.tsdc.data.service.CollectorsService
import com.example.tsdc.ui.viewmodel.CollectorsViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ColeccionistasActivity : ComponentActivity() {
    @OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TSDCTheme {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://back-vinilos-603543136039.us-central1.run.app/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(CollectorsService::class.java)
                val repository = CollectorsRepository(service)
                val viewModel = CollectorsViewModel(repository)

                ColeccionistasScreen(
                    viewModel = viewModel,
                    onBack = { finish() }
                )
            }
        }
    }
}
