package com.example.tsdc.ui.view

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tsdc.R
import com.example.tsdc.data.model.TrackDto
import com.example.tsdc.ui.state.TrackSelectionUiState
import com.example.tsdc.ui.viewmodel.TrackSelectionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackSelectionScreen(
    albumId: Int,
    viewModel: TrackSelectionViewModel,
    onBack: () -> Unit,
    onAssociate: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(albumId) {
        viewModel.loadTracksForAlbum(albumId)
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
                    Text("Asociar Canciones", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    ) { padding ->
        when (uiState) {
            is TrackSelectionUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            
            is TrackSelectionUiState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Error: ${(uiState as TrackSelectionUiState.Error).message}",
                        color = Color.Red
                    )
                }
            }
            
            is TrackSelectionUiState.Empty -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp)
                ) {
                    Text("No se encontraron canciones disponibles")
                }
            }
            
            is TrackSelectionUiState.Success -> {
                val successState = uiState as TrackSelectionUiState.Success
                TrackSelectionContent(
                    tracks = successState.allTracks,
                    selectedTracks = successState.selectedTracks,
                    filterText = successState.filterText,
                    onFilterTextChanged = { viewModel.updateFilterText(it) },
                    onTrackToggled = { viewModel.toggleTrackSelection(it) },
                    onAssociate = {
                        viewModel.associateTracksWithAlbum()
                        onAssociate()
                    },
                    onCancel = onBack,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackSelectionContent(
    tracks: List<TrackDto>,
    selectedTracks: List<TrackDto>,
    filterText: String,
    onFilterTextChanged: (String) -> Unit,
    onTrackToggled: (TrackDto) -> Unit,
    onAssociate: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Filter TextField
        OutlinedTextField(
            value = filterText,
            onValueChange = onFilterTextChanged,
            label = { Text("Filtrar canciones") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )
        
        // List of tracks with checkboxes
        val filteredTracks = if (filterText.isBlank()) {
            tracks
        } else {
            tracks.filter { it.name.contains(filterText, ignoreCase = true) }
        }
        
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(filteredTracks) { track ->
                val isSelected = selectedTracks.any { it.id == track.id }
                TrackItem(
                    track = track,
                    isSelected = isSelected,
                    onToggle = { onTrackToggled(track) }
                )
            }
        }
        
        // Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onCancel,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text("CANCELAR")
            }
            
            Button(
                onClick = onAssociate
            ) {
                Text("ASOCIAR")
            }
        }
    }
}

@Composable
fun TrackItem(
    track: TrackDto,
    isSelected: Boolean,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onToggle() }
            )
            
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = track.name,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Duración: ${track.duration}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}