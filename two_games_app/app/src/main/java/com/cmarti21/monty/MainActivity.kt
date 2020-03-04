package com.cmarti21.monty


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cmarti21.monty.CardFragment.CardFragmentListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), CardFragmentListener {

    var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cards_button.setOnClickListener{
            onCardsClick()
        }

        tictactoe_button.setOnClickListener{
            onTTTClick()
        }
    }

    override fun onNewCardGame(){
        score++
        updateScore()
    }

    private fun onCardsClick() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.gameLayout, CardFragment())
            .commitNow()

        score++
        updateScore()
    }

    private fun onTTTClick() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.gameLayout, TTTFragment())
            .commitNow()

        score++
        updateScore()
    }

    private fun updateScore(){
        var score_number = getString(R.string.score_text) + score
        score_update.setText(score_number)
    }

}
