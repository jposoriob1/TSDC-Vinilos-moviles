package com.example.tsdc.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tsdc.data.repository.CollectorsRepository
import com.example.tsdc.data.service.CollectorsService
import com.example.tsdc.ui.theme.TSDCTheme
import com.example.tsdc.ui.viewmodel.CollectorDetailViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CollectorDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val collectorId = intent.getIntExtra("collectorId", -1)
        if (collectorId == -1) finish()

        setContent {
            TSDCTheme {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(CollectorsService::class.java)
                val repository = CollectorsRepository(service)
                val viewModel = CollectorDetailViewModel(repository)

                CollectorDetailScreen(
                    collectorId = collectorId,
                    viewModel = viewModel,
                    onBack = { finish() }
                )
            }
        }
    }
}
