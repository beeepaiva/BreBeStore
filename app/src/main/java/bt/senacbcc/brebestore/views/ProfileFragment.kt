package bt.senacbcc.brebestore.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import bt.senacbcc.brebestore.R
import bt.senacbcc.brebestore.activities.LoginActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
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

        profileView.btnPurchHist.setOnClickListener{
            val frag = HistoryFragment()
            activity?.let {
                it.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragContainer, frag)
                    .commit()
            }
        }
        return profileView
    }

}