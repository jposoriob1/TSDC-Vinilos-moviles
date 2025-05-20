package com.example.tsdc.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tsdc.R
import com.example.tsdc.data.model.CollectorDto
import com.example.tsdc.ui.state.CollectorDetailUiState
import com.example.tsdc.ui.theme.Purple80
import com.example.tsdc.ui.viewmodel.CollectorDetailViewModel
import kotlin.math.roundToInt
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectorDetailScreen(
    collectorId: Int,
    viewModel: CollectorDetailViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(collectorId) {
        viewModel.fetchCollectorById(collectorId)
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
                    Text("VINYLS", fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Detalle del Coleccionista", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    ) { padding ->
        when (uiState) {
            is CollectorDetailUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is CollectorDetailUiState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Error: ${(uiState as CollectorDetailUiState.Error).message}",
                        color = Color.Red
                    )
                }
            }

            is CollectorDetailUiState.Empty -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp)
                ) {
                    Text("No se encontró el coleccionista")
                }
            }

            is CollectorDetailUiState.Success -> {
                val collector = (uiState as CollectorDetailUiState.Success).collector
                CollectorDetailContent(collector, Modifier.padding(padding))
            }
        }
    }
}

@Composable
fun CollectorDetailContent(collector: CollectorDto, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    val density = LocalDensity.current

    val thumbHeightDp = 40.dp
    val thumbHeightPx = with(density) { thumbHeightDp.roundToPx() }

    var trackHeightPx by remember { mutableStateOf(0) }

    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Purple80),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box {
            Column(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .verticalScroll(scrollState)
                    .padding(16.dp)
            ) {
                Text(collector.name, style = MaterialTheme.typography.titleLarge)
                Text("Teléfono: ${collector.telephone}")
                Text("Correo: ${collector.email}")

                Spacer(modifier = Modifier.height(12.dp))

                Text("Comentarios", fontWeight = FontWeight.Bold)
                collector.comments.forEach {
                    Text("• ${it.description} (${it.rating}/5)")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text("Artistas Favoritos", fontWeight = FontWeight.Bold)
                collector.favoritePerformers.forEach {
                    Text("• ${it.name}")
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(it.image)
                            .crossfade(true)
                            .build(),
                        contentDescription = it.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .padding(bottom = 8.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text("Álbumes Adquiridos", fontWeight = FontWeight.Bold)
                collector.collectorAlbums.forEach {
                    Text("• Álbum ID: ${it.id} - Precio: \$${it.price} - Estado: ${it.status}")
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (scrollState.maxValue > 0) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .fillMaxHeight()
                        .width(8.dp)
                        .padding(vertical = 16.dp)
                        .background(
                            Color.LightGray.copy(alpha = 0.4f),
                            RoundedCornerShape(4.dp)
                        )
                        .onGloballyPositioned { coordinates ->
                            trackHeightPx = coordinates.size.height
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .width(6.dp)
                            .height(thumbHeightDp)
                            .offset {
                                val scrollableSpacePx = trackHeightPx - thumbHeightPx
                                val availableScrollSpace = scrollableSpacePx.coerceAtLeast(0)
                                val scrollFraction = if (scrollState.maxValue > 0) {
                                    scrollState.value.toFloat() / scrollState.maxValue.toFloat()
                                } else {
                                    0f
                                }
                                val thumbOffsetYPx = (scrollFraction * availableScrollSpace)
                                    .coerceIn(0f, availableScrollSpace.toFloat())

                                IntOffset(x = 0, y = thumbOffsetYPx.roundToInt())
                            }
                            .background(
                                Color.DarkGray.copy(alpha = 0.6f),
                                RoundedCornerShape(3.dp)
                            )
                    )
                }
            }

        }
    }
}
