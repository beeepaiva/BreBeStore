package bt.senacbcc.brebestore.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import bt.senacbcc.brebestore.R
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.activity_about.view.*

class AboutFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {

        val aboutView = inflater.inflate(R.layout.activity_about, container, false)

        aboutView.imgSenac.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW,
            Uri.parse("https://www.sp.senac.br/"))
            startActivity(i)
        }

        aboutView.imgGitHub.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW,
                Uri.parse("https://github.com/beeepaiva/BreBeStore"))
            startActivity(i)
        }
        aboutView.imgEmail.setOnClickListener{
            val intent =
                Intent(Intent.ACTION_VIEW,
                    Uri.parse("mailto:" + "brebebcc@gmail.com")
                )
            startActivity(intent)
        }

        return aboutView
    }

}