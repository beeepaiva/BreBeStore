package bt.senacbcc.brebestore.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import bt.senacbcc.brebestore.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_product.view.*
import kotlinx.android.synthetic.main.product_page.*

class ProductPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_page)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val nome = intent.getStringExtra("nome")
        val desc = intent.getStringExtra("desc")
        val preco = intent.getStringExtra("preco")
        val urlImg = intent.getStringExtra("urlImg")
        val id = intent.getStringExtra("id")
        val categoria = intent.getStringExtra("categoria")

        nomeProduto.text = nome?.capitalize()
        descProduto.text = desc?.capitalize()
        precoProduto.text = "R$" + preco
        Picasso.get().load(urlImg).into(imgProduto)

        btnMaisQtd.setOnClickListener{
            var qtd = etQtd.text.toString()
            var teste = qtd.toInt() + 1
            etQtd.setText(Integer.toString(teste))
        }
        btnMenosQtd.setOnClickListener{
            var qtdm = etQtd.text.toString()
            var teste = 1
            if(qtdm.toInt() > 1)
                 teste = qtdm.toInt() - 1
            etQtd.setText(Integer.toString(teste))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val i = Intent(this, MainActivity::class.java)
        i.putExtra("fragment", "home")
        startActivity(i)
        return true
    }
}