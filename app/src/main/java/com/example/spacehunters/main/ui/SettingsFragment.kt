package com.example.spacehunters.main.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.example.spacehunters.R
import com.example.spacehunters.databinding.SettingsFragmentBinding


class SettingsFragment : Fragment() {
    private var _binding : SettingsFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chooseThemeRg.setOnCheckedChangeListener { group, checkedId ->
            activity?.let {
                 var curThemeId = it.getPreferences(Context.MODE_PRIVATE)
                    .getInt(THEME_PROP_NAME, R.style.Theme_SpaceHunters)
                var chosedThemeId : Int = R.style.Theme_SpaceHunters
                when (checkedId) {
                    R.id.marsian_theme_radiobutton -> chosedThemeId = R.style.MarsianTheme
                    R.id.standard_cosmic_theme_radiobutton -> chosedThemeId = R.style.StandardCosmicTheme
                    R.id.default_theme_radiobutton -> chosedThemeId = R.style.Theme_SpaceHunters
                }
                if (chosedThemeId != curThemeId) {
                    saveTheme(chosedThemeId)
                    it.recreate()
                }
            }

        }
    }

    private fun saveTheme(themeId: Int) {
        activity?.let {
            with(it.getPreferences(Context.MODE_PRIVATE).edit()) {
                putInt(THEME_PROP_NAME, themeId)
                apply()
            }
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
     //TODO   viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_settings).setEnabled(false).setVisible(false)
    }


}