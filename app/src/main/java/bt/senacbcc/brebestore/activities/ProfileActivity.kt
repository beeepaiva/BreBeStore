package bt.senacbcc.brebestore.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bt.senacbcc.brebestore.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*

private var mAuth: FirebaseAuth? = null


class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val user = FirebaseAuth.getInstance().currentUser

        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

            txtName.setText(name)
            txtEmail.setText(email)
            imgProfile.setImageURI(photoUrl)
        }

        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

}