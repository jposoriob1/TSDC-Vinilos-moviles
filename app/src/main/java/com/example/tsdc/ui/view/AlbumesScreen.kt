package com.example.tsdc.ui.view
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tsdc.data.model.AlbumDto
import com.example.tsdc.ui.viewmodel.AlbumsViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.res.painterResource
import com.example.tsdc.R
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.foundation.clickable
import com.example.tsdc.ui.state.AlbumsUiState




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumesScreen(
    viewModel: AlbumsViewModel,
    onBack: () -> Unit,
    onAlbumClick: (Int) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.fetchAlbums()
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                IconButton(
                    onClick = { onBack() },
                    modifier = Modifier
                        //.align(Alignment.CenterStart)
                        .offset(y = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver"
                    )
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(80.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("TSDC", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = "VINYLS",
                        fontSize = 14.sp,
                        color = Color(0xFF9C27B0),
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Álbumes", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    CreateAlbumButton()
                }
            }
        }
    ) { paddingValues ->

        val uiState by viewModel.uiState.collectAsState()

        // Este bloque reemplaza lo que tenías antes (if albums.isEmpty() else ...)
        when (uiState) {
            is AlbumsUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is AlbumsUiState.Error -> {
                val errorMsg = (uiState as AlbumsUiState.Error).message
                Text(
                    text = "Error al cargar: $errorMsg",
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(16.dp),
                    color = Color.Red
                )
            }

            is AlbumsUiState.Empty -> {
                Text(
                    text = "No hay álbumes disponibles",
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(16.dp)
                )
            }

            is AlbumsUiState.Success -> {
                val albums = (uiState as AlbumsUiState.Success).albums
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .testTag("lista_albumes")
                ) {
                    items(albums) { album ->
                        AlbumItem(album = album, onClick = { onAlbumClick(album.id) })
                    }
                }
            }
        }
    }
}
@Composable
fun CreateAlbumButton() {
    val context = LocalContext.current

    Button(
        onClick = {
            val intent = Intent(context, CreateAlbumActivity::class.java)
            context.startActivity(intent)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .testTag("boton_crear_album"),
    ) {
        Text(text = "CREAR ÁLBUM +")
    }
}

@Composable
fun AlbumItem(album: AlbumDto, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD0BCFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = album.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF4A148C) // Púrpura oscuro
                )
                Text(
                    text = "Género: ${album.genre}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Lanzamiento: ${album.releaseDate.take(10)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            AsyncImage(
                model = album.cover,
                contentDescription = "Carátula de ${album.name}",
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }
    }
}
