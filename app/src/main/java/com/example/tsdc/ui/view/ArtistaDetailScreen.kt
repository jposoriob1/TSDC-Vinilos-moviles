package com.example.tsdc.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.tsdc.R
import com.example.tsdc.data.model.TipoArtistaDto
import com.example.tsdc.ui.state.ArtistaDetailUiState
import com.example.tsdc.ui.viewmodel.ArtistaDetailViewModel
import com.example.tsdc.data.model.BandaDto
import com.example.tsdc.data.model.MusicoDto
import com.example.tsdc.ui.theme.Purple80
import androidx.compose.ui.platform.LocalDensity

@Composable
fun ArtistaDetailScreen(
    artistaId: Int,
    tipo: TipoArtistaDto,
    viewModel: ArtistaDetailViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(artistaId, tipo) {
        viewModel.cargarDetalle(artistaId, tipo)
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
//                    Spacer(modifier = Modifier.height(4.dp))
//                    Text("Detalle del Artista", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
        ) {
            when (uiState) {
                is ArtistaDetailUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is ArtistaDetailUiState.Error -> {
                    val msg = (uiState as ArtistaDetailUiState.Error).message
                    Text("Error: $msg", color = Color.Red, modifier = Modifier.padding(16.dp))
                }

                is ArtistaDetailUiState.SuccessMusico -> {
                    val musico = (uiState as ArtistaDetailUiState.SuccessMusico).musico
                    MusicoDetailContent(musico)
                }

                is ArtistaDetailUiState.SuccessBanda -> {
                    val banda = (uiState as ArtistaDetailUiState.SuccessBanda).banda
                    BandaDetailContent(banda)
                }
        }
    }
}
}

@Composable
fun MusicoDetailContent(musico: MusicoDto) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        // Título fuera del Card
        Text(
            text = "Información del Músico",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(scrollState),
            colors = CardDefaults.cardColors(containerColor = Purple80),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(musico.name, style = MaterialTheme.typography.titleLarge, modifier = Modifier.testTag("detalle_nombre_musico"))
                Text("Fecha de nacimiento: ${formatFecha(musico.birthDate)}")

                Spacer(modifier = Modifier.height(8.dp))

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(musico.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Foto del músico",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .background(Color.Gray)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("Descripción", fontWeight = FontWeight.Bold)
                Text(musico.description, modifier = Modifier.testTag("detalle_descripcion_musico"))

                Spacer(modifier = Modifier.height(8.dp))

                Text("Álbumes", fontWeight = FontWeight.Bold)
                musico.albums.forEach {
                    Text("• ${it.name}", modifier = Modifier.testTag("detalle_Album_${it.name}"))
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}



@Composable
fun BandaDetailContent(banda: BandaDto) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            text = "Información de la Banda",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(scrollState),
            colors = CardDefaults.cardColors(containerColor = Purple80),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(banda.name, style = MaterialTheme.typography.titleLarge, modifier = Modifier.testTag("detalle_nombre_musico"))
                Text("Fecha de creación: ${formatFecha(banda.creationDate)}")

                Spacer(modifier = Modifier.height(16.dp))

                AsyncImage(
                    model = banda.image,
                    contentDescription = "Imagen de la banda",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text("Descripción", style = MaterialTheme.typography.titleMedium)
                Text(banda.description)

                Spacer(modifier = Modifier.height(12.dp))
                Text("Álbumes", style = MaterialTheme.typography.titleMedium)
                banda.albums.forEach {
                    Text("• ${it.name}")
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}




