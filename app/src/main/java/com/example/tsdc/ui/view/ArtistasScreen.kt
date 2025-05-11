package com.example.tsdc.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.tsdc.R
import com.example.tsdc.data.model.*
import com.example.tsdc.ui.viewmodel.ArtistasViewModel
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material.icons.filled.Check
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.platform.testTag


@Composable
fun ArtistasScreen(
    viewModel: ArtistasViewModel,
    onBack: () -> Unit,
    onItemClick: (Int) -> Unit
) {
    val artistas by viewModel.artistas.observeAsState(emptyList())
    val tipoSeleccionado by viewModel.tipoSeleccionado.observeAsState(TipoArtistaDto.MUSICO)

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                IconButton(
                    onClick = { onBack() },
                    modifier = Modifier.offset(y = 10.dp)
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
                    Text("Artistas", style = MaterialTheme.typography.titleLarge, modifier=Modifier.testTag("lista_artistas"))
                }
            }
        }
    ) { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {
            val primaryColor = MaterialTheme.colorScheme.primary
            val onPrimaryColor = MaterialTheme.colorScheme.onPrimary
            val borderColor = MaterialTheme.colorScheme.outline

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .height(40.dp)
            ) {
                val items = listOf(
                    TipoArtistaDto.MUSICO to "MÚSICOS",
                    TipoArtistaDto.BANDA to "BANDAS"
                )

                items.forEachIndexed { index, (tipo, label) ->
                    val seleccionado = tipo == tipoSeleccionado

                    val shape = when (index) {
                        0 -> RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
                        1 -> RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp)
                        else -> RoundedCornerShape(0.dp)
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(
                                color = if (seleccionado) primaryColor else Color.White,
                                shape = shape
                            )
                            .border(
                                width = 1.dp,
                                color = borderColor,
                                shape = shape
                            )
                            .clickable { viewModel.cambiarTipo(tipo) },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (seleccionado) {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = "Seleccionado",
                                    tint = onPrimaryColor,
                                    modifier = Modifier
                                        .size(16.dp)
                                        .padding(end = 4.dp)
                                )
                            }
                            Text(
                                text = label,
                                color = if (seleccionado) onPrimaryColor else Color(0xFF1C1B1F),
                                fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(artistas) { artista ->
                    ArtistaItem(artista = artista, onClick = { onItemClick(artista.id) })
                }
            }
        }
    }
}

@Composable
fun ArtistaItem(artista: ArtistaDto, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() }
            .testTag("card_artista_${artista.name}"),
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
                    text = artista.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF4A148C)
                )
                Text(
                    text = artista.description.take(80) + "...",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            AsyncImage(
                model = artista.image,
                contentDescription = "Imagen de ${artista.name}",
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}
