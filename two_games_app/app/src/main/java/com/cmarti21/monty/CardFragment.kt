package com.cmarti21.monty

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cmarti21.monty.model.Cards
import com.cmarti21.monty.model.Sign
import kotlinx.android.synthetic.main.fragment_card.*

class CardFragment : Fragment() {

    private val model = Cards()

    private lateinit var matchResult: Triple<Sign, Sign, Sign>

    private var counter: Int = 0
    private var counter2: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        card_left.visibility = View.INVISIBLE
        card_right.visibility = View.INVISIBLE
        reset_button.isEnabled = false


        play_button.setOnClickListener{
            play_button.isEnabled = false
            reset_button.isEnabled = true

            matchResult = model.playGame()
            playCards()
        }

        card_left.setOnClickListener{
            cardRotation(l = true, r = false, c = false)
        }

        card_right.setOnClickListener{
            cardRotation(l = false, r = true, c = false)
        }

        start_cardView.setOnClickListener{
            cardRotation(l = false, r = false, c = true)
        }


        reset_button.setOnClickListener{
            counter = 0
            counter2 = 0
            start_cardView.setImageResource(R.drawable.card)
            resetCards()
            play_button.isEnabled = true
            reset_button.isEnabled = false
        }
    }

    interface CardFragmentListener {
        fun onNewCardGame()
    }

    private lateinit var listener: CardFragmentListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = activity as CardFragmentListener
    }


    private fun playCards(){

        val animatorL =
            ObjectAnimator.ofFloat(card_left, "translationX", Cards.TO_THE_LEFT_ANIM, Cards.LEFT_CARD)
        val animatorR =
            ObjectAnimator.ofFloat(card_right, "translationX", Cards.TO_THE_RIGHT_ANIM, Cards.RIGHT_CARD)

        animatorL.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                card_left.setImageResource(R.drawable.card)
                card_left.visibility = View.VISIBLE
            }
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {}
        })

        animatorR.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                card_right.setImageResource(R.drawable.card)
                start_cardView.setImageResource(R.drawable.card)
                card_right.visibility = View.VISIBLE
            }
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {}
        })

        val set = AnimatorSet()
        set.play(animatorL)
        set.play(animatorR)
        set.duration = Cards.CARD_ANIM_DURATION
        set.start()
    }

    private fun resetCards(){

        listener.onNewCardGame()

        val animatorL =
            ObjectAnimator.ofFloat(card_left, "translationX", Cards.LEFT_CARD, Cards.TO_THE_LEFT_ANIM)
        val animatorR =
            ObjectAnimator.ofFloat(card_right, "translationX", Cards.RIGHT_CARD, Cards.TO_THE_RIGHT_ANIM)

        animatorL.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                card_left.visibility = View.INVISIBLE
            }
        })

        animatorR.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                card_right.visibility = View.INVISIBLE
            }
        })

        val set = AnimatorSet()
        set.play(animatorL)
        set.play(animatorR)
        set.duration = Cards.CARD_ANIM_DURATION
        set.start()
    }

    fun random(): Boolean {
        var t: Boolean = false
        var randomInteger = (0..1).shuffled().first()

        if(randomInteger == 0){
            ++counter
            if(counter == 3){
                t = false
            } else {
                t = true
            }

        } else{
            ++counter2
            if((counter2 == 2) || (counter2 == 3)) {
                t = true
            } else {
                t = false
            }
        }
        return t
    }

    private fun cardRotation(l: Boolean, r: Boolean, c: Boolean) {

        val animatorL1 =
            ObjectAnimator.ofFloat(card_left, "scaleX", 1f, 0f)
        val animatorL2 =
            ObjectAnimator.ofFloat(card_left, "scaleX", 0f, -1f)
        val animatorR1 =
            ObjectAnimator.ofFloat(card_right, "scaleX", 1f, 0f)
        val animatorR2 =
            ObjectAnimator.ofFloat(card_right, "scaleX", 0f, -1f)
        val animatorC1 =
            ObjectAnimator.ofFloat(start_cardView, "scaleX", 1f, 0f)
        val animatorC2 =
            ObjectAnimator.ofFloat(start_cardView, "scaleX", 0f, -1f)

        animatorL1.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {

                card_left.setImageResource(
                    getImageId(matchResult.first, left = random())
                )
            }
        })

        animatorL2.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {}
        })

        animatorR1.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {

                card_right.setImageResource(
                    getImageId(matchResult.first, left = random())
                )
            }
        })

        animatorR2.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {}
        })

        animatorC1.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {
                start_cardView.setImageResource(
                    getImageId(matchResult.first, left = random())
                )
            }
        })

        animatorC2.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {}
        })

        val set = AnimatorSet()
        if (l == true){
            set.play(animatorL1).before(animatorL2)
        } else if (r == true) {
            set.play(animatorR1).before(animatorR2)
        } else if (c == true) {
            set.play(animatorC1).before(animatorC2)
        }
        set.duration = Cards.CARD_ANIM_DURATION
        set.start()
    }

    private fun getImageId(sign: Sign, left: Boolean) = when (sign) {
        Sign.RIGHT -> if (left) R.drawable.blank_card else R.drawable.winner_card
        Sign.CENTER -> if (left) R.drawable.blank_card else R.drawable.winner_card
        Sign.LEFT -> if (left) R.drawable.blank_card else R.drawable.winner_card
    }

}