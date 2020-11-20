package bt.senacbcc.brebestore.activities

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import bt.senacbcc.brebestore.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.auth.UserProfileChangeRequest
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
            val pht = it.photoUrl

            profileView.txtName.setText(name)
            profileView.txtEmail.setText(email)
            profileView.imgPerfil.setImageURI(pht)
        }

        profileView.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        profileView.ibtnEdit.setOnClickListener {
            if(profileView.btnSave.visibility == View.INVISIBLE){
                profileView.txtName.setEnabled(true)
                profileView.txtEmail.setEnabled(true)
                profileView.btnSave.setVisibility(View.VISIBLE)
            }else{
                profileView.txtName.setEnabled(false)
                profileView.txtEmail.setEnabled(false)
                profileView.btnSave.setVisibility(View.INVISIBLE)
            }
        }

        profileView.btnSave.setOnClickListener {

            //Fazer lógica de mandar os novos valores para o Firebase

            val userProfileChangeRequest = userProfileChangeRequest {
                displayName = profileView.txtName.text.toString()
            }

            user!!.updateProfile(userProfileChangeRequest)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Snackbar.make(profileView.layout,"Nome alterado com sucesso!", Snackbar.LENGTH_LONG)
                    }
                }

            user!!.updateEmail(profileView.txtEmail.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Snackbar.make(profileView.layout,"Email alterado com sucesso!", Snackbar.LENGTH_LONG)
                    }
                }
            profileView.txtName.setEnabled(false)
            profileView.txtEmail.setEnabled(false)
            profileView.btnSave.setVisibility(View.INVISIBLE)

            activity?.let{
                it.supportFragmentManager
                    .beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();
            }

        }

        profileView.btnpurchHist.setOnClickListener{
            //Fazer lógica de abrir página com historico de compras
        }

        return profileView
    }

}