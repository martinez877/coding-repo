package com.cmarti21.shoppinglist.ui.main

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.*
import androidx.fragment.app.Fragment
import com.cmarti21.shoppinglist.MainActivity.Companion.BIG_TEXT
import com.cmarti21.shoppinglist.MainActivity.Companion.SHOW_MESSAGE_AT_START
import com.cmarti21.shoppinglist.MainActivity.Companion.SOUND_ON

import com.cmarti21.shoppinglist.R
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    private val prefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sound_on_switch.isChecked = prefs.getBoolean(SOUND_ON, false)
        sound_on_switch.setOnCheckedChangeListener { _, isChecked ->
            with(prefs.edit()) {
                putBoolean(SOUND_ON, isChecked)
                apply()
            }
        }
        show_message_switch.isChecked = prefs.getBoolean(SHOW_MESSAGE_AT_START, false)
        show_message_switch.setOnCheckedChangeListener { _, isChecked ->
            with(prefs.edit()) {
                putBoolean(SHOW_MESSAGE_AT_START, isChecked)
                apply()
            }
        }

        bigger_text_switch.isChecked = prefs.getBoolean(BIG_TEXT, false)
        sound_on_switch.setOnCheckedChangeListener { _, isChecked ->
            with(prefs.edit()) {
                putBoolean(BIG_TEXT, isChecked)
                apply()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }
}
