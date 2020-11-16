package bt.senacbcc.brebestore.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bt.senacbcc.brebestore.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        imgSenac.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW,
            Uri.parse("https://www.sp.senac.br/"))
            startActivity(i)
        }

        imgGitHub.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW,
                Uri.parse("https://github.com/beeepaiva/BreBeStore"))
            startActivity(i)
        }
        imgEmail.setOnClickListener{
            val intent =
                Intent(Intent.ACTION_VIEW,
                    Uri.parse("mailto:" + "brebebcc@gmail.com")
                )
            startActivity(intent)
        }
    }
}