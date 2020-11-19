package bt.senacbcc.brebestore.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceFragmentCompat
import bt.senacbcc.brebestore.R

class SettingsActivity : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {

        val settingsView = inflater.inflate(R.layout.settings_activity, container, false)

        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.settings, SettingsFragment())
            ?.commit()

        return settingsView
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }

}