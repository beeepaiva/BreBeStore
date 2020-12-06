package bt.senacbcc.brebestore.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import bt.senacbcc.brebestore.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val auth = FirebaseAuth.getInstance()

        application.setTheme(R.style.Theme_MyApp)

        if (auth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            Toast.makeText(this, "Usuario logado como " + auth.currentUser?.displayName, Toast.LENGTH_LONG).show()
            startActivity(intent)
        }else{
            createSignInIntent()
        }

    }

    private fun createSignInIntent() {
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.ic_round_shopping_cart_24)
                .build(),
            RC_SIGN_IN)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                //Toast.makeText(this, "Usuario logado como: " + auth.currentUser.displayName, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Erro ao logar", Toast.LENGTH_LONG).show()
            }
        }
    }

}

