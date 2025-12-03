
package com.TheWolf1724.impostor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.TheWolf1724.impostor.model.GameConfig
import com.TheWolf1724.impostor.model.selectImpostorWithWeightedRepeat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Forzar modo claro
        androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO)
		setContent {
			ImpostorAppUI()
		}
	}
}

@Composable
fun ImpostorAppUI() {
    var step by remember { mutableStateOf(0) }
    var playerNames by remember { mutableStateOf(listOf<String>()) }
    var impostorCount by remember { mutableStateOf(0) }
    var impostorIndexes by remember { mutableStateOf(listOf<Int>()) }
    var inputName by remember { mutableStateOf("") }
    var playerCount by remember { mutableStateOf(0) }

    var selectedWordWithHints by remember { mutableStateOf<com.TheWolf1724.impostor.model.WordWithHints?>(null) }
    var selectedHint by remember { mutableStateOf("") }

    // Nuevo: modo de selección de impostores
    var impostorMode by remember { mutableStateOf("fixed") } // "fixed" o "random"

    // Estado para el historial de impostor único aleatorio
    var lastImpostorIndex by remember { mutableStateOf<Int?>(null) }
    var lastImpostorRepeatCount by remember { mutableStateOf(0) }

    fun selectRandomWordAndHint() {
        val wordWithHints = com.TheWolf1724.impostor.model.WordBank.words.random()
        selectedWordWithHints = wordWithHints
        selectedHint = wordWithHints.hints.random()
    }



    when (step) {
        0 -> PlayerSetupScreen(
            onPlayersSet = { names ->
                playerNames = names
                playerCount = names.size
                step = 1
            }
        )

        1 -> ImpostorSelectionScreen(
            playerCount = playerCount,
            impostorMode = impostorMode,
            onImpostorCountSelected = { count ->
                impostorCount = count
                impostorIndexes = GameConfig.selectImpostors(playerCount, count)
                impostorMode = "fixed"
                selectRandomWordAndHint()
                step = 2
            },
            onRandom = {
                val count = GameConfig.calculateImpostorCount(playerCount)
                impostorCount = count
                impostorIndexes = GameConfig.selectImpostors(playerCount, count)
                impostorMode = "random"
                selectRandomWordAndHint()
                step = 2
            }
        )
        2 -> WordRevealScreen(
            playerNames = playerNames,
            impostorIndexes = impostorIndexes,
            word = selectedWordWithHints?.word ?: "",
            hint = selectedHint,
            onFinish = { step = 3 }
        )
        3 -> GameTimerScreen(
            impostorIndexes = impostorIndexes,
            playerNames = playerNames,
            onNewGame = {
                // Reinicia todo
                step = 0
                playerNames = listOf()
                impostorCount = 0
                impostorIndexes = listOf()
                inputName = ""
                playerCount = 0
                impostorMode = "fixed"
            },
            onNewRound = {
                // Mantiene jugadores, actualiza impostores según modo
                if (impostorMode == "random") {
                    val count = GameConfig.calculateImpostorCount(playerNames.size)
                    impostorCount = count
                    // Si solo hay 1 impostor, aplicar la lógica especial
                    if (count == 1) {
                        val newImpostorIndex = selectImpostorWithWeightedRepeat(
                            playerNames.size,
                            lastImpostorIndex,
                            lastImpostorRepeatCount
                        )
                        impostorIndexes = listOf(newImpostorIndex)
                        // Actualizar el contador de repeticiones
                        if (lastImpostorIndex == newImpostorIndex) {
                            lastImpostorRepeatCount = lastImpostorRepeatCount + 1
                        } else {
                            lastImpostorRepeatCount = 1
                            lastImpostorIndex = newImpostorIndex
                        }
                    } else {
                        impostorIndexes = GameConfig.selectImpostors(playerNames.size, count)
                        lastImpostorIndex = null
                        lastImpostorRepeatCount = 0
                    }
                } else {
                    impostorIndexes = GameConfig.selectImpostors(playerNames.size, impostorCount)
                    lastImpostorIndex = null
                    lastImpostorRepeatCount = 0
                }
                selectRandomWordAndHint()
                step = 2 // Volver a mostrar palabras
            }
        )
    }
}
