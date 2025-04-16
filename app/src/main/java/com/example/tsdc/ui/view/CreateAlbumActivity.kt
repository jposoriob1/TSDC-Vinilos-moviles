package com.example.tsdc.ui.view

import CreateAlbumScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tsdc.ui.theme.TSDCTheme

class CreateAlbumActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TSDCTheme {
                CreateAlbumScreen(
                    onBack = { finish() }
                )
            }
        }
    }
}
