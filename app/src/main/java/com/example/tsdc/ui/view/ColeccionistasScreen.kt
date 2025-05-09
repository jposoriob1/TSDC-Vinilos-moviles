package com.example.tsdc.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tsdc.R
import com.example.tsdc.data.model.CollectorDto
import com.example.tsdc.ui.state.CollectorsUiState
import com.example.tsdc.ui.viewmodel.CollectorsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColeccionistasScreen(
    viewModel: CollectorsViewModel,
    onBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.fetchCollectors()
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
                    Text("Coleccionistas", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    ) { paddingValues ->

        val uiState by viewModel.uiState.collectAsState()

        when (uiState) {
            is CollectorsUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is CollectorsUiState.Error -> {
                val errorMsg = (uiState as CollectorsUiState.Error).message
                Text(
                    text = "Error al cargar: $errorMsg",
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(16.dp),
                    color = Color.Red
                )
            }

            is CollectorsUiState.Empty -> {
                Text(
                    text = "No hay coleccionistas disponibles",
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(16.dp)
                )
            }

            is CollectorsUiState.Success -> {
                val collectors = (uiState as CollectorsUiState.Success).collectors
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .testTag("lista_coleccionistas")
                ) {
                    items(collectors) { collector ->
                        CollectorItem(collector = collector)
                    }
                }
            }
        }
    }
}

@Composable
fun CollectorItem(collector: CollectorDto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD0BCFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = collector.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF4A148C) // Púrpura oscuro
            )
            Text(
                text = collector.email,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}