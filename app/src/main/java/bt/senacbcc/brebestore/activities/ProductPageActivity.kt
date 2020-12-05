package bt.senacbcc.brebestore.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import bt.senacbcc.brebestore.R
import bt.senacbcc.brebestore.db.AppDatabase
import bt.senacbcc.brebestore.model.Product
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
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
            etQtd.setText(Integer.toString(qtd.toInt() + 1))
        }
        btnMenosQtd.setOnClickListener{
            var qtdm = etQtd.text.toString()
            var qtdFinal = 1
            if(qtdm.toInt() > 1)
                qtdFinal = qtdm.toInt() - 1
            etQtd.setText(Integer.toString(qtdFinal))
        }


        btnBuy.setOnClickListener{
            val prod =  Product(
                name = nome.toString(),
                desc = desc.toString(),
                price = preco.toString().toFloat(),
                qtd = etQtd.text.toString().toInt(),
                urlImg = urlImg.toString()
            )
            buyItem(prod)
        }
    }

    //Item vai direto pro carrinho
    private fun buyItem(produto: Product){
        Thread{
            insertProduct(produto)
            finish()
        }.start()
    }

    fun insertProduct(product: Product){
        val roomdb = Room.databaseBuilder(this, AppDatabase::class.java, "AppDB").build()
        roomdb.productDao().insert(product)
        Snackbar.make(
            coordinatorLayoutPageProduct,
            "Produto adicionado com sucesso ao carrinho!",
            Snackbar.LENGTH_LONG
        ).show()
        Thread.sleep(2000)

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val i = Intent(this, MainActivity::class.java)
        i.putExtra("fragment", "home")
        startActivity(i)
        return true
    }


}