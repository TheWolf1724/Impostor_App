package com.TheWolf1724.impostor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow

@Composable
fun ImpostorSelectionScreen(
    playerCount: Int,
    impostorMode: String,
    onImpostorCountSelected: (Int) -> Unit,
    onRandom: () -> Unit
) {
    var impostorCount by remember { mutableStateOf(1) }
    val maxImpostors = playerCount
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFFFC1CC), // rosa pastel
            Color(0xFFFFE4EC), // rosa muy claro
            Color(0xFFF8BBD0)  // rosa pastel más intenso
        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            buildString {
                append("Selecciona el número de ")
            },
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 48.dp, bottom = 0.dp)
        )
        Text(
            "impostores:",
            color = Color(0xFFE91E63),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Slider(
            value = impostorCount.toFloat(),
            onValueChange = { impostorCount = it.toInt() },
            valueRange = 1f..maxImpostors.toFloat(),
            steps = (maxImpostors - 1).coerceAtLeast(0),
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.White,
                inactiveTrackColor = Color.White.copy(alpha = 0.3f)
            ),
            modifier = Modifier.fillMaxWidth(0.85f)
        )
        Text(
            "Seleccionados: $impostorCount",
            color = Color(0xFFE91E63),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { onImpostorCountSelected(impostorCount) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White.copy(alpha = 0.15f),
                contentColor = Color.White
            ),
            shape = MaterialTheme.shapes.medium,
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .shadow(6.dp, ambientColor = Color.Black.copy(alpha = 0.10f), spotColor = Color.Black.copy(alpha = 0.10f),
                    shape = MaterialTheme.shapes.medium)
        ) {
            Text("Seleccionar", fontWeight = FontWeight.SemiBold)
        }
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = onRandom,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White.copy(alpha = 0.15f),
                contentColor = Color.White
            ),
            shape = MaterialTheme.shapes.medium,
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .shadow(6.dp, ambientColor = Color.Black.copy(alpha = 0.10f), spotColor = Color.Black.copy(alpha = 0.10f),
                    shape = MaterialTheme.shapes.medium)
        ) {
            Text("Impostores Aleatorios", fontWeight = FontWeight.SemiBold)
        }
    }
}
