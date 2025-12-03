package com.TheWolf1724.impostor.model

import kotlin.random.Random

/**
 * Selecciona un impostor único, reduciendo la probabilidad de que salga el mismo varias veces seguidas.
 * Las dos primeras veces: probabilidad uniforme. Desde la tercera, la probabilidad del repetido baja progresivamente.
 * Si sale otro, se reinicia el contador.
 */
fun selectImpostorWithWeightedRepeat(
    playerCount: Int,
    lastImpostorIndex: Int?,
    lastImpostorRepeatCount: Int
): Int {
    if (playerCount == 0) return 0
    // Si no hay historial, elegir aleatorio
    if (lastImpostorIndex == null) return (0 until playerCount).random()

    // Probabilidad base para todos
    val baseProb = 1.0 / playerCount
    // A partir de la tercera vez, la probabilidad del repetido baja
    val repeatMultipliers = listOf(1.0, 1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1)
    val repeatMultiplier = if (lastImpostorRepeatCount < repeatMultipliers.size) repeatMultipliers[lastImpostorRepeatCount] else 0.1

    // Probabilidades para cada jugador
    val weights = MutableList(playerCount) { baseProb }
    weights[lastImpostorIndex] = baseProb * repeatMultiplier

    // Normalizar para que sumen 1
    val total = weights.sum()
    val normalized = weights.map { it / total }

    // Selección ponderada
    val roll = Random.nextDouble()
    var acc = 0.0
    for (i in 0 until playerCount) {
        acc += normalized[i]
        if (roll < acc) return i
    }
    return playerCount - 1 // fallback
}

// ...existing code...

// Representa la configuración del juego
class GameConfig(
    val playerNames: List<String>,
    val impostorCount: Int,
    val impostorIndexes: List<Int>
) {
    companion object {
        /**
         * Calcula el número de impostores según las reglas:
         * - Máximo la mitad de jugadores
         * - 10% probabilidad de que todos sean impostores
         * - 10% probabilidad de que no haya ninguno
         * - Resto: entre 1 y maxImpostors
         */
        fun calculateImpostorCount(playerCount: Int): Int {
            if (playerCount == 0) return 0
            val rand = Random.nextInt(100)
            // 2% probabilidad de que todos sean impostores
            if (rand >= 98) return playerCount
            // 2% probabilidad de que no haya ninguno
            if (rand < 2) return 0

            // Rango de impostores según cantidad de jugadores
            return when {
                playerCount <= 3 -> 1
                playerCount in 4..6 -> 2
                playerCount in 7..12 -> 4
                else -> (playerCount / 3).coerceAtLeast(1)
            }
        }

        /**
         * Selecciona los impostores aleatoriamente
         */
        fun selectImpostors(playerCount: Int, impostorCount: Int): List<Int> {
            return (0 until playerCount).shuffled().take(impostorCount)
        }

    }
}
