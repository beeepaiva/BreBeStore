package bt.senacbcc.brebestore.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import bt.senacbcc.brebestore.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.view.*

private var mAuth: FirebaseAuth? = null


class ProfileActivity : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {

        val profileView = inflater.inflate(R.layout.activity_profile, container, false)

        val user = FirebaseAuth.getInstance().currentUser

        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email

            profileView.txtName.text = name
            profileView.txtEmail.text = email
        }

        profileView.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        return profileView
    }

}