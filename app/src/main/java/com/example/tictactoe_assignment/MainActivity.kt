package com.example.tictactoe_assignment

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tictactoe_assignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    enum class Turn{
        CIRCLE,
        CROSS
    }

    private var firstTurn = Turn.CROSS
    private var currentTurn = Turn.CROSS

    private var crossesScore = 0
    private var circlesScore = 0

    private var victory = false

    private var boardList = mutableListOf<Button>()

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()
    }

    private fun initBoard() {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    fun boardTapped(view: View) {
        if(view !is Button)
            return
        addToBoard(view)

        if(checkForWinner(CIRCLE)){
            victory = true
            circlesScore++
            result("Circle WINS!")
        }

        if(checkForWinner(CROSS)){
            victory = true
            crossesScore++
            result("Cross WINS!")
        }

        if(fullBoard() && !victory){
            result("Draw")
        }
    }

    private fun checkForWinner(s: String): Boolean {

        //Horizontal Victory
        if(match(binding.a1, s) && match(binding.a2, s) && match(binding.a3, s))
            return true
        if(match(binding.b1, s) && match(binding.b2, s) && match(binding.b3, s))
            return true
        if(match(binding.c1, s) && match(binding.c2, s) && match(binding.c3, s))
            return true

        //Vertical Victory
        if(match(binding.a1, s) && match(binding.b1, s) && match(binding.c1, s))
            return true
        if(match(binding.a2, s) && match(binding.b2, s) && match(binding.c2, s))
            return true
        if(match(binding.a3, s) && match(binding.b3, s) && match(binding.c3, s))
            return true

        //Diagonal Victory
        if(match(binding.a1, s) && match(binding.b2, s) && match(binding.c3, s))
            return true
        if(match(binding.a3, s) && match(binding.b2, s) && match(binding.c1, s))
            return true

        return false
    }

    private fun match(button: Button, symbol: String): Boolean = button.text == symbol

    private fun result(title: String) {
        val message = "\n Circles: $circlesScore \n\n Crosses: $crossesScore"
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Reset"){
                _,_ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()
    }

    private fun resetBoard() {
        for(button in boardList){
            button.text = ""
        }
        if(firstTurn == Turn.CIRCLE)
            firstTurn = Turn.CROSS
        else if(firstTurn == Turn.CROSS)
            firstTurn = Turn.CIRCLE

        currentTurn = firstTurn
        victory = false
        setTurnLabel()
    }

    private fun fullBoard(): Boolean {
        for(button in boardList){
            if(button.text == "")
                return false
        }
        return true
    }

    private fun addToBoard(button: Button) {
        if(button.text != "")
            return

        if(currentTurn == Turn.CIRCLE){
            button.text = CIRCLE
            currentTurn = Turn.CROSS
        } else if(currentTurn == Turn.CROSS){
            button.text = CROSS
            currentTurn = Turn.CIRCLE
        }
        setTurnLabel()
    }

    private fun setTurnLabel() {
        var turnText =""
        if(currentTurn == Turn.CROSS)
            turnText = "$CROSS Turn"
        else if(currentTurn == Turn.CIRCLE)
            turnText = "$CIRCLE Turn"

        binding.turnTV.text = turnText
    }

    companion object{
        const val CIRCLE = "O"
        const val CROSS = "X"
    }
}