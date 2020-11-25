package bt.senacbcc.brebestore.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import bt.senacbcc.brebestore.R
import bt.senacbcc.brebestore.views.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_principal.view.*

private var mAuth: FirebaseAuth? = null

class MainActivity : AppCompatActivity() {
    var toggle: ActionBarDrawerToggle? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = getCurrentUser()

        val header = navigationView.getHeaderView(0)
        header.txtNome.text = user?.displayName
        header.txtEmail.text = user?.email

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
            else if(it.itemId == R.id.cart){
                val frag = CartFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag).commit()
                true
            }
            else if (it.itemId == R.id.profile) {
                val frag = ProfileActivity()
                supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag).commit()
                true
            }
            else if (it.itemId == R.id.settings) {
                val frag = SettingsFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag).commit()
                true
            }
            else if (it.itemId == R.id.about) {
                val frag = AboutFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag).commit()
                true
            }
            false
        }

    }

    fun getCurrentUser(): FirebaseUser? {
        val aut = FirebaseAuth.getInstance()
        return aut.currentUser
    }

    fun configDB(){

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        toggle?.let{
            return it.onOptionsItemSelected(item)
        }
        return false
    }
}

