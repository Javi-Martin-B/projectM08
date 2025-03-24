package com.example.gambledlive

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var currentPlayer = "X"
    private val board = Array(3) { arrayOfNulls<String>(3) }
    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar el tablero
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j] = ""
                val buttonId = resources.getIdentifier("button_$i$j", "id", packageName)
                val button = findViewById<Button>(buttonId)
                button.text = "" // Limpiar el texto del botón
                button.setOnClickListener { v: View -> onButtonClick(v, i, j) }
            }
        }
    }

    fun onButtonClick(view: View, row: Int, col: Int) {
        val clickedButton = view as Button

        // Si la casilla ya está ocupada o el juego terminó, no hacer nada
        if (!gameActive || board[row][col] != "") {
            return
        }

        // Colocar la marca del jugador
        board[row][col] = currentPlayer
        clickedButton.text = currentPlayer

        // Verificar si alguien ganó
        if (checkWinner()) {
            gameActive = false
            Toast.makeText(this, "$currentPlayer gana!", Toast.LENGTH_SHORT).show()
        } else {
            // Cambiar de jugador
            currentPlayer = if (currentPlayer == "X") "O" else "X"
        }
    }

    private fun checkWinner(): Boolean {
        // Verificación de ganador (filas, columnas, diagonales)
        for (i in 0..2) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) {
                return true
            }
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) {
                return true
            }
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            return true
        }
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            return true
        }
        return false
    }

    fun restartGame(view: View?) {
        // Reiniciar el juego
        currentPlayer = "X"
        gameActive = true

        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j] = ""
            }
        }

        // Limpiar los botones
        for (i in 0..2) {
            for (j in 0..2) {
                val buttonId = resources.getIdentifier("button_$i$j", "id", packageName)
                val button = findViewById<Button>(buttonId)
                button.text = "" // Limpiar el texto del botón
            }
        }
        // Botón para ir al siguiente nivel (FourInARow1)
        val btnNxtLvl: Button = findViewById(R.id.btnNxtLvl)

        btnNxtLvl.setOnClickListener {
            val intent = Intent(this, FourInARow1::class.java)
            startActivity(intent)
        }
    }
}
