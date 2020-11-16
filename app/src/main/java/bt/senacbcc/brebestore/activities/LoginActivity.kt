package bt.senacbcc.brebestore.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bt.senacbcc.brebestore.R
import com.google.firebase.auth.FirebaseAuth


private var mAuth: FirebaseAuth? = null

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }
}