package bt.senacbcc.brebestore.views

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import bt.senacbcc.brebestore.R
import bt.senacbcc.brebestore.db.AppDatabase
import bt.senacbcc.brebestore.model.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_cartproduct.view.*
import kotlinx.android.synthetic.main.card_product.view.*
import kotlinx.android.synthetic.main.card_product.view.card_body
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_home.*


class CartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val cartView = inflater.inflate(R.layout.fragment_cart, cartContainer, false)
        refreshProducts()
        return cartView
    }


    fun refreshProducts(){
        Thread {
            activity?.let{
                val db = Room.databaseBuilder(it, AppDatabase::class.java, "AppDB").build()
                val allProducts = db.productDao().getAll()

                this.activity?.runOnUiThread {
                    updateUI(allProducts)
                }
            }
        }.start()
    }
    fun updateUI(productList: List<Product>){
        cartContainer.removeAllViews()

        for (product in productList){

            val card = layoutInflater.inflate(
                R.layout.card_cartproduct,
                cartContainer,
                false
            )
            card.card_titleCart.text = product.name.capitalize()
            card.card_bodyCart.text = "R$ " + product.price

            val imgUrl = product.urlImg.toString()
            Picasso.get().load(imgUrl).into(card.card_headerCart)

            card.btnRemoveItem.setOnClickListener(removeItem(product))
            cartContainer.addView(card)
        }

    }
    fun removeItem(product: Product): View.OnClickListener? {
        return View.OnClickListener { _ ->
            Thread{
            val produtoSelecionado =
                Product(name = product.name, desc = product.desc, price = product.price, id = product.id, qtd =product.qtd?.minus(1), urlImg = "")
            deleteProduct(produtoSelecionado)
            refreshProducts()
            }.start()

        }

    }
    fun deleteProduct(product: Product){
        activity?.let {
            val db = Room.databaseBuilder(it, AppDatabase::class.java, "AppDB").build()
            db.productDao().delete(product)
        }
    }


}