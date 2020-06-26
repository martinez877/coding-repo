package com.cmarti21.shoppinglist

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.cmarti21.shoppinglist.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navHostFragment: NavHostFragment

    private val prefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        NavigationUI.setupActionBarWithNavController(this, navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ -> supportActionBar?.let {
            it.title = when (destination.id) {
                R.id.settingsFragment -> getString(R.string.settings)
                R.id.infoFragment -> getString(R.string.info)
                else -> getString(R.string.app_name)
            }
        }
        }
    }

    override fun onSupportNavigateUp() = Navigation.findNavController(this, R.id.navHostFragment).navigateUp()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_menuItem -> {
                //TODO: Delete all list items
                true
            }R.id.settings_menuItem -> {
                navHostFragment.navController.navigate(R.id.action_mainFragment_to_settingsFragment)
                true
            }R.id.info_menuItem -> {
                navHostFragment.navController.navigate(R.id.action_mainFragment_to_infoFragment)
                true
            }else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val SOUND_ON = "sound_on"
        const val SHOW_MESSAGE_AT_START = "show_message_at_start"
        const val BIG_TEXT = "big_text_on"
    }

}
