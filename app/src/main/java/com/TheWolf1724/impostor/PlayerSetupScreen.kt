package com.TheWolf1724.impostor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.shadow

@Composable
fun PlayerSetupScreen(onPlayersSet: (List<String>) -> Unit) {
    var playerNames by remember { mutableStateOf(listOf<String>()) }
    var inputName by remember { mutableStateOf("") }

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
            "Añade los nombres de los jugadores:",
            style = TextStyle(
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 20.dp, bottom = 24.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.weight(1f)) {
                BasicTextField(
                    value = inputName,
                    onValueChange = { inputName = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .align(Alignment.BottomStart)
                        .background(Color.White)
                        .padding(top = 10.dp)
                        .padding(bottom = 10.dp)
                )
            }
            Spacer(Modifier.width(16.dp))
            Button(
                onClick = {
                    if (inputName.isNotBlank()) {
                        playerNames = playerNames + inputName.trim()
                        inputName = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.15f),
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium,
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                modifier = Modifier
                    .shadow(6.dp, ambientColor = Color.Black.copy(alpha = 0.10f), spotColor = Color.Black.copy(alpha = 0.10f),
                        shape = MaterialTheme.shapes.medium)
            ) {
                Text("Añadir", fontWeight = FontWeight.SemiBold)
            }
        }
        Spacer(Modifier.height(16.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            for (name in playerNames) {
                Text(
                    name,
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = { if (playerNames.size > 2) onPlayersSet(playerNames) },
            enabled = playerNames.size > 2,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White.copy(alpha = 0.15f),
                contentColor = Color.White
            ),
            shape = MaterialTheme.shapes.medium,
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .then(
                    if (playerNames.size > 2)
                        Modifier.shadow(6.dp, ambientColor = Color.Black.copy(alpha = 0.10f), spotColor = Color.Black.copy(alpha = 0.10f),
                            shape = MaterialTheme.shapes.medium)
                    else Modifier
                )
        ) {
            Text("Empezar", fontWeight = FontWeight.SemiBold)
        }
    }
}
