package com.TheWolf1724.impostor

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.TheWolf1724.impostor.model.WordBank
import kotlin.random.Random
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.ui.draw.shadow

@Composable
fun WordRevealScreen(
    playerNames: List<String>,
    impostorIndexes: List<Int>,
    word: String,
    hint: String,
    onFinish: () -> Unit
) {
    var currentPlayer by remember { mutableStateOf(0) }
    var revealed by remember { mutableStateOf(false) }
    var dragOffset by remember { mutableStateOf(0f) }
    val totalPlayers = playerNames.size

    // Selección aleatoria de palabra y pista (solo una vez por ronda)
    val (selectedWord, selectedHint) = remember {
        val wordWithHints = WordBank.words.random()
        val hint = wordWithHints.hints.random()
        wordWithHints.word to hint
    }
    val isImpostor = impostorIndexes.contains(currentPlayer)

    val screenGradient = Brush.verticalGradient(
        listOf(
            Color(0xFFFFC1CC),
            Color(0xFFFFE4EC),
            Color(0xFFF8BBD0)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenGradient)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Nombre del jugador en mayúsculas y negrita
        Text(
            text = "Jugador: ${playerNames[currentPlayer]}",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Color.Black
        )
        Spacer(Modifier.height(32.dp))

        // Cuadro deslizable
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Color.White.copy(alpha = 0.15f), shape = MaterialTheme.shapes.medium)
                .pointerInput(Unit) {
                    detectVerticalDragGestures(
                        onVerticalDrag = { _, dragAmount ->
                            dragOffset += dragAmount
                            if (dragOffset < -60f) revealed = true
                        },
                        onDragEnd = {
                            dragOffset = 0f
                            revealed = false
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            if (!revealed) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Flecha arriba",
                        tint = Color(0xFF222222),
                        modifier = Modifier.size(40.dp)
                    )
                    Text(
                        "Desliza",
                        color = Color(0xFF222222),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            } else {
                if (isImpostor) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("IMPOSTOR", color = Color.Red, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(10.dp))
                        Text("Pista: $hint", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Palabra:", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(8.dp))
                        Text(word, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        // Botón fuera del cuadro deslizable
        Button(
            onClick = {
                if (currentPlayer < totalPlayers - 1) {
                    currentPlayer++
                    revealed = false
                } else {
                    onFinish()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .shadow(6.dp, ambientColor = Color.Black.copy(alpha = 0.10f), spotColor = Color.Black.copy(alpha = 0.10f),
                    shape = MaterialTheme.shapes.medium),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White.copy(alpha = 0.15f),
                contentColor = Color.White
            ),
            shape = MaterialTheme.shapes.medium,
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Text(
                if (currentPlayer < totalPlayers - 1) "Siguiente" else "Comenzar juego",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
        }
    }
}
