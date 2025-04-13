package com.example.firstproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tsdc.ui.theme.TSDCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TSDCTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        /*Image(
            painter = painterResource(id = R.drawable.logo), // put your image in res/drawable as logo.png
            contentDescription = "Logo",
            modifier = Modifier.size(140.dp),
            contentScale = ContentScale.Fit
        )*/

        Spacer(modifier = Modifier.height(12.dp))

        Text("TSDC", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Text(
            text = "VINYLS",
            fontSize = 14.sp,
            color = Color(0xFF9C27B0), // purple tone
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(32.dp))

        ButtonBox("ALBUMES")
        Spacer(modifier = Modifier.height(16.dp))
        ButtonBox("ARTISTAS")
        Spacer(modifier = Modifier.height(16.dp))
        ButtonBox("COLECCIONISTAS")
    }
}

@Composable
fun ButtonBox(text: String) {
    OutlinedButton(
        onClick = { /* TODO: Handle click */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
    ) {
        Text(text)
    }
}
