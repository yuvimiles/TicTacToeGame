package com.example.tictactoegame

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var currentPlayer = "X"
    private val board = Array(3) { arrayOfNulls<String>(3) }
    private var gameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // מקשר בין הכפתורים בתצוגה
        val buttons = arrayOf(
            findViewById<Button>(R.id.button0),
            findViewById<Button>(R.id.button1),
            findViewById<Button>(R.id.button2),
            findViewById<Button>(R.id.button3),
            findViewById<Button>(R.id.button4),
            findViewById<Button>(R.id.button5),
            findViewById<Button>(R.id.button6),
            findViewById<Button>(R.id.button7),
            findViewById<Button>(R.id.button8)
        )

        val statusTextView = findViewById<TextView>(R.id.statusTextView)
        val playAgainButton = findViewById<Button>(R.id.playAgainButton)

        // מאזין ללחיצות על הכפתורים
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                if (button.text.isEmpty() && !gameOver) {
                    button.text = currentPlayer
                    val row = index / 3
                    val col = index % 3
                    board[row][col] = currentPlayer

                    if (checkWin()) {
                        statusTextView.text = "Player $currentPlayer Wins!"
                        gameOver = true
                        playAgainButton.visibility = Button.VISIBLE
                    } else if (isDraw()) {
                        statusTextView.text = "It's a Draw!"
                        playAgainButton.visibility = Button.VISIBLE
                    } else {
                        currentPlayer = if (currentPlayer == "X") "O" else "X"
                        statusTextView.text = "Player $currentPlayer's Turn"
                    }
                }
            }
        }

        // מאזין ללחיצה על כפתור "Play Again"
        playAgainButton.setOnClickListener {
            resetGame(buttons, statusTextView)
        }
    }

    private fun checkWin(): Boolean {
        // בדיקת שורות, עמודות ואלכסונים
        for (i in 0..2) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) return true
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) return true
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true
        return false
    }

    private fun isDraw(): Boolean {
        // בדיקה אם כל התאים מלאים
        return board.all { row -> row.all { it != null } }
    }

    private fun resetGame(buttons: Array<Button>, statusTextView: TextView) {
        // איפוס הלוח והכפתורים
        board.forEach { row -> row.fill(null) }
        buttons.forEach { it.text = "" }
        currentPlayer = "X"
        gameOver = false
        statusTextView.text = "Player X's Turn"
        findViewById<Button>(R.id.playAgainButton).visibility = Button.GONE
    }
}
