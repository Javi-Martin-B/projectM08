package com.example.gambledlive

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class FourInARow1 : AppCompatActivity() {
    private val board: Array<IntArray> = Array(6) { IntArray(7) }
    private var currentPlayer = 1
    private var gameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.four_in_a_row) // Asegúrate de crear este layout
        Log.d("FourInARow", "Juego iniciado")
    }

    fun placePiece(column: Int): Boolean {
        if (gameOver) {
            Log.d("FourInARow", "El juego ha terminado.")
            return false
        }

        for (row in 5 downTo 0) {
            if (board[row][column] == 0) {
                board[row][column] = currentPlayer
                if (checkWin(row, column)) {
                    gameOver = true
                    Log.d("FourInARow", "¡Jugador $currentPlayer gana!")
                }
                currentPlayer = if (currentPlayer == 1) 2 else 1
                return true
            }
        }
        Log.d("FourInARow", "Columna $column está llena. Elige otra.")
        return false
    }

    private fun checkWin(row: Int, col: Int): Boolean {
        return checkDirection(row, col, 1, 0) ||
                checkDirection(row, col, 0, 1) ||
                checkDirection(row, col, 1, 1) ||
                checkDirection(row, col, 1, -1)
    }

    private fun checkDirection(row: Int, col: Int, deltaRow: Int, deltaCol: Int): Boolean {
        var count = 1
        var r = row + deltaRow
        var c = col + deltaCol

        while (r in 0..5 && c in 0..6 && board[r][c] == currentPlayer) {
            count++
            r += deltaRow
            c += deltaCol
        }

        r = row - deltaRow
        c = col - deltaCol
        while (r in 0..5 && c in 0..6 && board[r][c] == currentPlayer) {
            count++
            r -= deltaRow
            c -= deltaCol
        }

        return count >= 4
    }

    fun printBoard() {
        for (row in board) {
            Log.d("FourInARow", row.joinToString(" ") { if (it == 0) "." else if (it == 1) "X" else "O" })
        }
    }
}
