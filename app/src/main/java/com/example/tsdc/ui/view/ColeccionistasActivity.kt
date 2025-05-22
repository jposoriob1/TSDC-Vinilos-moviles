package com.example.tsdc.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tsdc.ui.theme.TSDCTheme
import com.example.tsdc.data.repository.CollectorsRepository
import com.example.tsdc.data.service.CollectorsService
import com.example.tsdc.ui.viewmodel.CollectorsViewModel
import com.example.tsdc.data.local.VinylRoomDatabase
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
                    .baseUrl("http://10.0.2.2:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(CollectorsService::class.java)


                val database = VinylRoomDatabase.getDatabase(applicationContext)
                val collectorsDao = database.collectorsDao()


                val repository = CollectorsRepository(service, collectorsDao)
                val viewModel = CollectorsViewModel(repository)

                ColeccionistasScreen(
                    viewModel = viewModel,
                    onBack = { finish() }
                )
            }
        }
    }
}
