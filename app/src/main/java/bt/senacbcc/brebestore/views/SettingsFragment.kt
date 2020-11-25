package bt.senacbcc.brebestore.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceFragmentCompat
import bt.senacbcc.brebestore.R


class SettingsActivity : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val settingsView = inflater.inflate(R.layout.settings_activity, container, false)

        activity?.let{
            it.supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }

        /*val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity)

        val themeColor = sharedPrefs.getBoolean("dark_theme_switch", false)

        if (themeColor) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }*/



        /*val darkThemeSwitch: SwitchMaterial = settingsView.findViewById(R.id.dark_theme_switch)

        darkThemeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){



            }
        }*/


        return settingsView
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}