package bt.senacbcc.brebestore.activities

import android.content.Intent
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

            when (it.itemId) {
                R.id.home -> {
                    val frag = HomeFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag)
                        .commit()
                    true
                }
                R.id.cart -> {
                    val frag = CartFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag)
                        .commit()
                    true
                }
                R.id.profile -> {
                    val frag = ProfileActivity()
                    supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag)
                        .commit()
                    true
                }
                R.id.about -> {
                    val frag = AboutFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag)
                        .commit()
                    true
                }
                else ->
                    false
            }
        }

        val frag = HomeFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragContainer, frag).commit()
    }

    fun getCurrentUser(): FirebaseUser? {
        val aut = FirebaseAuth.getInstance()
        return aut.currentUser
    }

    fun mostrarPaginaProduto(produto: Map<String, Any>){
        val i = Intent(this, ProductPageActivity::class.java)
        for(prod in produto){
            i.putExtra(prod.key, prod.value.toString())
        }
        startActivity(i)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        toggle?.let{
            return it.onOptionsItemSelected(item)
        }
        return false
    }
}

