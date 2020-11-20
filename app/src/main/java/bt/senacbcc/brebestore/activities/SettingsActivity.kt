package bt.senacbcc.brebestore.activities

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import bt.senacbcc.brebestore.R
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.android.synthetic.main.settings_activity.*


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