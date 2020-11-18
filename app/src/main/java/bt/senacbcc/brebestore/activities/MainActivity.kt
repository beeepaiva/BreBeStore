package bt.senacbcc.brebestore.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import bt.senacbcc.brebestore.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_principal.txtNome
import kotlinx.android.synthetic.main.menu_principal.txtEmail


private var mAuth: FirebaseAuth? = null

class MainActivity : AppCompatActivity() {
    var toggle: ActionBarDrawerToggle? = null
    var name = ""
    var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val user = FirebaseAuth.getInstance().currentUser

        user?.let {
            // Name, email address
            name = "" + it.displayName
            email = "" + it.email
        }

        Snackbar.make(fragContainer, "Usuario logado como " + name, Snackbar.LENGTH_LONG).show()

        /*toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open_menu, R.string.close_menu){
            override fun onDrawerClosed(view:View){
                super.onDrawerClosed(view)
                //toast("Drawer closed")
            }

            override fun onDrawerOpened(drawerView: View){
                super.onDrawerOpened(drawerView)
                txtNome.setText(name)
                txtEmail.setText(email)
            }
        }*/


        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open_menu, R.string.close_menu)
        /*{
            override fun onDrawerClosed(view:View){
                super.onDrawerClosed(view)
                //toast("Drawer closed")
            }

            override fun onDrawerOpened(drawerView: View){
                super.onDrawerOpened(drawerView)
                //toast("Drawer opened")
            }*/


        toggle?.let{
            drawerLayout.addDrawerListener(it)
            it.syncState()
        }


        navigationView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawers()

            if (it.itemId == R.id.profile) {
                val frag = HomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag).commit()
                true
            }
            /*else if (it.itemId == R.id.settings) {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
            if (it.itemId == R.id.about) {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
            }*/
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

