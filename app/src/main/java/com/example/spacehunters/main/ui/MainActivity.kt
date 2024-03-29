package com.example.spacehunters.main.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.spacehunters.R
import com.example.spacehunters.databinding.MainActivityBinding

val THEME_PROP_NAME: String = "AppTheme"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        processThemeSettings()
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_earth -> {
                    openFragment(EpicPhotosFragment.newInstance())
                    // Toast.makeText(applicationContext, "Doesn't work!!!", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.bottom_view_mars -> {
                    openFragment(PhotoFromMarsFragment.newInstance())
                    true
                }
                R.id.bottom_view_cosmic_weather -> {
                    Toast.makeText(
                        applicationContext,
                        "Cosmic weather fragment",
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }
                else -> false
            }
        }
        //binding.bottomNavigationView.selectedItemId = R.id.bottom_view_earth
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

    }

    fun processThemeSettings() {
        var themeId: Int = -1
        try {
            themeId = getPreferences(Context.MODE_PRIVATE).getInt(
                THEME_PROP_NAME,
                R.style.Theme_SpaceHunters
            )
        } catch (e: Exception) { // Cleanup if settings are corrupted
            getPreferences(Context.MODE_PRIVATE).edit().clear()
                .apply()
        }
        mySetTheme(themeId)
    }


    fun mySetTheme(themeId: Int?) {
        when (themeId) {
            R.style.MarsianTheme -> setTheme(R.style.MarsianTheme)
            R.style.StandardCosmicTheme -> setTheme(R.style.StandardCosmicTheme)
            else -> setTheme(R.style.Theme_SpaceHunters)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_settings -> {
                openFragment(SettingsFragment.newInstance())
                true
            }
            R.id.action_favorites -> {
                //TODO openFragment(FavouritesFragment.newInstance())
                Toast.makeText(
                    applicationContext,
                    getString(R.string.toast_for_favourites_fragment),
                    Toast.LENGTH_SHORT
                )
                    .show()
                true
            }
            R.id.action_main -> {
                openFragment(MainFragment.newInstance())
                true
            }
            R.id.action_notes -> {
                openFragment(NotesFragment.newInstance())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.apply {
            beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }
}