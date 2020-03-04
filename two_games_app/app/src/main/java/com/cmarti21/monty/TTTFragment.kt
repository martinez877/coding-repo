package com.cmarti21.monty


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_ttt.*


class TTTFragment : Fragment(), View.OnClickListener {

    var player_turn: Boolean = true

    override fun onClick(v: View) {

        var turn = if (player_turn) "x" else "o"

        when(v.id){
            R.id.cardBtn1->{
                cardBtn1.setText(turn)
            }
            R.id.cardBtn2->{
                cardBtn2.setText(turn)
            }
            R.id.cardBtn3->{
                cardBtn3.setText(turn)
            }
            R.id.cardBtn4->{
                cardBtn4.setText(turn)
            }
            R.id.cardBtn5->{
                cardBtn5.setText(turn)
            }
            R.id.cardBtn6->{
                cardBtn6.setText(turn)
            }
            R.id.cardBtn7->{
                cardBtn7.setText(turn)
            }
            R.id.cardBtn8->{
                cardBtn8.setText(turn)
            }
            R.id.cardBtn9->{
                cardBtn9.setText(turn)
            }
        }
        player_turn = !player_turn

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ttt, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardBtn1.setOnClickListener(this)
        cardBtn2.setOnClickListener(this)
        cardBtn3.setOnClickListener(this)
        cardBtn4.setOnClickListener(this)
        cardBtn5.setOnClickListener(this)
        cardBtn6.setOnClickListener(this)
        cardBtn7.setOnClickListener(this)
        cardBtn8.setOnClickListener(this)
        cardBtn9.setOnClickListener(this)


    }

}
