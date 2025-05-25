package com.example.tsdc.ui.view

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tsdc.R
import com.example.tsdc.data.model.GenreDto
import com.example.tsdc.data.model.RecordLabelDto
import com.example.tsdc.ui.state.AlbumCreationState
import com.example.tsdc.ui.theme.Purple80
import com.example.tsdc.ui.viewmodel.AlbumCreateViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar

@Composable
fun accesibleTextFieldColors(): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAlbumScreen(viewModel: AlbumCreateViewModel, onBack: () -> Unit) {
    val context = LocalContext.current
    val state = viewModel.creationState
    val calendar = Calendar.getInstance()
    val selectedDate = remember { mutableStateOf<LocalDate?>(null) }
    var releaseDate by remember { mutableStateOf("") }

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val date = LocalDate.of(year, month + 1, dayOfMonth)
                selectedDate.value = date
                releaseDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    var name by remember { mutableStateOf("") }
    var cover by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf(GenreDto.FOLK) }
    var recordLabel by remember { mutableStateOf(RecordLabelDto.FUENTES) }

    if (state is AlbumCreationState.Success) {
        Toast.makeText(context, "Álbum creado exitosamente", Toast.LENGTH_LONG).show()
        onBack()
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
                    modifier = Modifier.offset(y = 20.dp)
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
                    Text("Crear Álbum", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    ) { padding ->
        Card(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Purple80),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") },
                    colors = accesibleTextFieldColors(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("album_nombre")
                )
                OutlinedTextField(
                    value = cover,
                    onValueChange = { cover = it },
                    label = { Text("URL de portada") },
                    colors = accesibleTextFieldColors(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("album_portada")
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { datePickerDialog.show() }
                ) {
                    OutlinedTextField(
                        value = releaseDate,
                        onValueChange = { releaseDate = it },
                        label = { Text("Fecha de lanzamiento") },
                        colors = accesibleTextFieldColors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { datePickerDialog.show() }
                            .testTag("album_fecha"),
//                        enabled = false
                    )

                }
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción") },
                    colors = accesibleTextFieldColors(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("album_descripcion")
                )

                Spacer(modifier = Modifier.height(8.dp))

                var expandedGenre by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expandedGenre,
                    onExpandedChange = { expandedGenre = !expandedGenre }
                ) {
                    OutlinedTextField(
                        value = genre.value,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Género") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGenre)
                        },
                        colors = accesibleTextFieldColors(),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .testTag("album_genero")
                    )
                    ExposedDropdownMenu(
                        expanded = expandedGenre,
                        onDismissRequest = { expandedGenre = false }
                    ) {
                        GenreDto.values().forEach {
                            DropdownMenuItem(
                                text = { Text(it.value) },
                                onClick = {
                                    genre = it
                                    expandedGenre = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                var expandedLabel by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expandedLabel,
                    onExpandedChange = { expandedLabel = !expandedLabel }
                ) {
                    OutlinedTextField(
                        value = recordLabel.value,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Sello discográfico") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLabel)
                        },
                        colors = accesibleTextFieldColors(),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .testTag("album_sello")
                    )
                    ExposedDropdownMenu(
                        expanded = expandedLabel,
                        onDismissRequest = { expandedLabel = false }
                    ) {
                        RecordLabelDto.values().forEach {
                            DropdownMenuItem(
                                text = { Text(it.name) },
                                onClick = {
                                    recordLabel = it
                                    expandedLabel = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            if (selectedDate.value == null) {
                                Toast.makeText(context, "Por favor selecciona una fecha", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            val zonedDate = selectedDate.value
                                ?.atStartOfDay(ZoneId.of("America/Bogota"))
                                ?.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

                            if (zonedDate == null) {
                                Toast.makeText(context, "Error formateando la fecha", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            viewModel.createAlbum(
                                name = name,
                                cover = cover,
                                releaseDate = zonedDate,
                                description = description,
                                genre = genre,
                                recordLabel = recordLabel
                            )
                        },
                        modifier = Modifier
                            .testTag("album_boton_crear"),
                    ) {
                        Text("Crear")
                    }

                    Button(onClick = { onBack() },
                        modifier = Modifier.testTag("btnCancelar")) {
                        Text("Cancelar")
                    }
                }

                if (state is AlbumCreationState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
                }

                if (state is AlbumCreationState.Error) {
                    Text(
                        text = state.message,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}
