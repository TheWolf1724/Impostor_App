package com.TheWolf1724.impostor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.ui.draw.shadow

@Composable
fun GameTimerScreen(
    impostorIndexes: List<Int>,
    playerNames: List<String>,
    onNewGame: () -> Unit,
    onNewRound: () -> Unit
) {
    var timeLeft by remember { mutableStateOf(300) } // 5 minutos
    var showReveal by remember { mutableStateOf(false) }
    var showImpostors by remember { mutableStateOf(false) }
    // Eliminar 'finished', no se usará

    LaunchedEffect(timeLeft) {
        if (timeLeft > 0 && !showReveal) {
            delay(1000)
            timeLeft--
        } else if (timeLeft == 0 && !showReveal) {
            showReveal = true
        }
    }

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
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Tiempo restante: ${timeLeft / 60}:${(timeLeft % 60).toString().padStart(2, '0')}", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(32.dp))
        if (!showReveal) {
            Button(
                onClick = { showReveal = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.15f),
                    contentColor = Color(0xFFE91E63)
                ),
                shape = MaterialTheme.shapes.medium,
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Text("Destapar impostor(es)", fontWeight = FontWeight.SemiBold)
            }
        } else {
            Text("El impostor era...", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { showImpostors = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.15f),
                    contentColor = Color(0xFFE91E63)
                ),
                shape = MaterialTheme.shapes.medium,
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Text("Descubrir", fontWeight = FontWeight.SemiBold)
            }
            if (showImpostors) {
                Spacer(Modifier.height(32.dp))
                for (i in impostorIndexes) {
                    Text(playerNames[i], color = Color(0xFFE91E63), fontSize = 36.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(32.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onNewGame,
                        modifier = Modifier
                            .weight(1f)
                            .shadow(6.dp, ambientColor = Color.Black.copy(alpha = 0.10f), spotColor = Color.Black.copy(alpha = 0.10f),
                                shape = MaterialTheme.shapes.medium),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.15f),
                            contentColor = Color.White
                        ),
                        shape = MaterialTheme.shapes.medium,
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                    ) {
                        Text("Nueva partida", fontWeight = FontWeight.SemiBold)
                    }
                    Button(
                        onClick = onNewRound,
                        modifier = Modifier
                            .weight(1f)
                            .shadow(6.dp, ambientColor = Color.Black.copy(alpha = 0.10f), spotColor = Color.Black.copy(alpha = 0.10f),
                                shape = MaterialTheme.shapes.medium),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.15f),
                            contentColor = Color.White
                        ),
                        shape = MaterialTheme.shapes.medium,
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                    ) {
                        Text("Siguiente ronda", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}
