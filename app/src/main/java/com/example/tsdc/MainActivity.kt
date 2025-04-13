package com.example.tsdc

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(140.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("TSDC", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Text(
            text = "VINYLS",
            fontSize = 14.sp,
            color = Color(0xFF9C27B0),
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(32.dp))

        val buttons = listOf(
            Pair("ALBUMES", Intent(context, AlbumesActivity::class.java)),
            Pair("ARTISTAS", Intent(context, ArtistasActivity::class.java)),
            Pair("COLECCIONISTAS", Intent(context, ColeccionistasActivity::class.java))
        )

        for (button in buttons) {
            ButtonBox(button, context)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ButtonBox(button: Pair<String, Intent>, context: Context) {
    OutlinedButton(
        onClick = {
            context.startActivity(button.second)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
    ) {
        Text(button.first)
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier) {
    TSDCTheme {
        Text(
            text = "Hello $name!",
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}