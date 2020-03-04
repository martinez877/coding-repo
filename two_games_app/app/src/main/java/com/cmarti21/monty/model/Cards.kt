package com.cmarti21.monty.model

import com.cmarti21.monty.model.Sign.Companion.randomSign
import kotlin.random.Random.Default.nextInt


    enum class Sign {
        RIGHT, CENTER, LEFT;

        companion object {
            fun randomSign(): Sign {
                return values()[nextInt(values().size)]
            }
        }
    }

class Cards {

    companion object {
        const val CARD_ANIM_DURATION = 500L
        const val TO_THE_LEFT_ANIM = 500f
        const val TO_THE_RIGHT_ANIM = -500f
        const val LEFT_CARD = -18f
        const val RIGHT_CARD = 18f
    }

    fun playGame(): Triple<Sign, Sign, Sign> {
        val leftCard = randomSign()
        val rightCard = randomSign()
        val centerCard = randomSign()
        return Triple (leftCard, rightCard, centerCard)
    }
}