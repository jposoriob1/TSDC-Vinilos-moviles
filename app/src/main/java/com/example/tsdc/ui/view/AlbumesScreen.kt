package com.example.tsdc.ui.view

import coil.compose.AsyncImage
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tsdc.data.model.AlbumDto
import com.example.tsdc.ui.viewmodel.AlbumsViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun AlbumesScreen(viewModel: AlbumsViewModel) {
    val albums by viewModel.albums.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAlbums()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Álbumes") })
        }
    ) { paddingValues ->
        if (albums.isEmpty()) {
            Text(
                text = "No hay álbumes disponibles 😢",
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            )
        } else {
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(albums) { album ->
                    AlbumItem(album)
                }
            }
        }
    }
}

@Composable
fun AlbumItem(album: AlbumDto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = album.cover,
                contentDescription = "Portada de ${album.name}",
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 16.dp)
            )

            Column {
                Text(
                    text = album.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(text = "Género: ${album.genre}")
                Text(text = "Lanzamiento: ${album.releaseDate.substring(0, 10)}")
            }
        }
    }
}
