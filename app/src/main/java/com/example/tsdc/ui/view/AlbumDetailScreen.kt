package com.example.tsdc.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tsdc.R
import com.example.tsdc.data.model.AlbumDto
import com.example.tsdc.ui.viewmodel.AlbumDetailViewModel
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.tsdc.ui.theme.Purple80
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import java.util.Locale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.testTag
import com.example.tsdc.ui.state.AlbumDetailUiState
import java.text.SimpleDateFormat
import kotlin.math.roundToInt
import android.content.Intent




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailScreen(
    albumId: Int,
    viewModel: AlbumDetailViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(albumId) {
        viewModel.fetchAlbumById(albumId)
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
                    Text("Detalle del Álbum", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    ) { padding ->
        when (uiState) {
            is AlbumDetailUiState.Loading -> {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is AlbumDetailUiState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Error: ${(uiState as AlbumDetailUiState.Error).message}",
                        color = Color.Red
                    )
                }
            }

            is AlbumDetailUiState.Empty -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp)
                ) {
                    Text("No se encontró el álbum")
                }
            }

            is AlbumDetailUiState.Success -> {
                val album = (uiState as AlbumDetailUiState.Success).album
                val context = LocalContext.current
                AlbumDetailContent(
                    album = album, 
                    modifier = Modifier.padding(padding),
                    onAssociateTrackClick = { albumId ->
                        val intent = Intent(context, TrackSelectionActivity::class.java)
                        intent.putExtra("albumId", albumId)
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
    }

@Composable
fun AlbumDetailContent(album: AlbumDto, modifier: Modifier = Modifier, onAssociateTrackClick: (Int) -> Unit = {}) {
    val scrollState = rememberScrollState()
    val density = LocalDensity.current
    val context = LocalContext.current

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
                Text(album.name, style = MaterialTheme.typography.titleLarge,modifier = Modifier.testTag("detalle_nombre_album"))
                Text("Fecha de lanzamiento: ${formatFecha(album.releaseDate)}")

                Spacer(modifier = Modifier.height(8.dp))

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(album.cover)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Carátula",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Gray)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("Descripción", fontWeight = FontWeight.Bold)
                Text(album.description, modifier = Modifier.testTag("detalle_descripcion_album"))

                Spacer(modifier = Modifier.height(8.dp))

                Text("Canciones", fontWeight = FontWeight.Bold)
                album.tracks.forEach {
                    Text("• ${it.name}", modifier = Modifier.testTag("detalle_track_${it.name}"))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = {
                        onAssociateTrackClick(album.id)
                    }) {
                        Text("ASOCIAR CANCIÓN +")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text("Género(s)", fontWeight = FontWeight.Bold)
                Text(album.genre, modifier = Modifier.testTag("detalle_genero_album"))

                Spacer(modifier = Modifier.height(8.dp))

                Text("Artista/Banda", fontWeight = FontWeight.Bold)
                album.performers.forEach {
                    Text("• ${it.name}",modifier = Modifier.testTag("detalle_artista_${it.name}"))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text("Sello discográfico", fontWeight = FontWeight.Bold)
                Text(album.recordLabel, modifier = Modifier.testTag("detalle_sello_album"))

                Spacer(modifier = Modifier.height(8.dp))

                Text("Comentarios", fontWeight = FontWeight.Bold)
                album.comments.forEach {
                    Text("• ${it.description}")
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


                                val scrollFraction =
                                    scrollState.value.toFloat() / scrollState.maxValue.toFloat()


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


fun formatFecha(fechaISO: String): String {
    return try {

        val formatoEntradaConMs = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        val fecha = try {
            formatoEntradaConMs.parse(fechaISO)
        } catch (e: Exception) {

            val formatoEntradaSinMs = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
            formatoEntradaSinMs.parse(fechaISO)
        }

        val formatoSalida = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale("es", "ES"))
        formatoSalida.format(fecha!!)
    } catch (e: Exception) {

        println("Error formateando fecha: $fechaISO - ${e.message}")
        fechaISO
    }
}
