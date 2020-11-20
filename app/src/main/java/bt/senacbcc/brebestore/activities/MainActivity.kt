package bt.senacbcc.brebestore.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import bt.senacbcc.brebestore.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_principal.view.*

private var mAuth: FirebaseAuth? = null

class MainActivity : AppCompatActivity() {
    var toggle: ActionBarDrawerToggle? = null
    var name = ""
    var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = FirebaseAuth.getInstance().currentUser

        user?.let {
            // Name, email address
            name = "" + it.displayName
            email = "" + it.email

            val header = navigationView.getHeaderView(0)
            header.txtNome.text = name
            header.txtEmail.text = email

        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)

        val themeColor = sharedPrefs.getBoolean("dark_theme_switch", false)

        if (themeColor) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        Snackbar.make(fragContainer, "Usuario logado como " + name, Snackbar.LENGTH_LONG).show()

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open_menu, R.string.close_menu)
        toggle?.let{
            drawerLayout.addDrawerListener(it)
            it.syncState()
        }

        navigationView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawers()

            if(it.itemId == R.id.home){
                val frag = HomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag).commit()
                true
            }
            else if (it.itemId == R.id.profile) {
                val frag = ProfileActivity()
                supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag).commit()
                true
            }
            else if (it.itemId == R.id.settings) {
                val frag = SettingsActivity()
                supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag).commit()
                true
            }
            else if (it.itemId == R.id.about) {
                val frag = AboutActivity()
                supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag).commit()
                true
            }
            false
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        toggle?.let{
            return it.onOptionsItemSelected(item)
        }
        return false
    }
}

