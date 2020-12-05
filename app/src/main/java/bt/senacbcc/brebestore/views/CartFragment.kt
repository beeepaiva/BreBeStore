package bt.senacbcc.brebestore.views

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.room.Room
import bt.senacbcc.brebestore.R
import bt.senacbcc.brebestore.UIutils.alert
import bt.senacbcc.brebestore.apis.ProductAPI
import bt.senacbcc.brebestore.db.AppDatabase
import bt.senacbcc.brebestore.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_cartproduct.view.*
import kotlinx.android.synthetic.main.card_product.view.*
import kotlinx.android.synthetic.main.card_product.view.card_body
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_cart.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class CartFragment : Fragment() {

    private var total : Float = .0f;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val cartView = inflater.inflate(R.layout.fragment_cart, cartContainer, false)
        refreshProducts(cartView)

        cartView.btnBuyCart.setOnClickListener {
            updateUserHistory(cartView)
        }

        return cartView
    }

    fun refreshProducts(cartView: View){
        Thread {
            activity?.let{
                val db = Room.databaseBuilder(it, AppDatabase::class.java, "AppDB").build()
                val allProducts = db.productDao().getAll()

                this.activity?.runOnUiThread {
                    total = .0f;
                    updateUI(allProducts, cartView)
                    cartView.txtTotal.text = total.toString();
                    if (total == .0f) {
                        cartView.bottomBar.visibility = View.GONE
                        cartView.txtEmpty.visibility = View.VISIBLE
                    } else {
                        cartView.bottomBar.visibility = View.VISIBLE
                        cartView.txtEmpty.visibility = View.GONE
                    }
                }
            }
        }.start()
    }

    fun updateUI(productList: List<Product>, cartView: View){
        cartContainer.removeAllViews()

        for (product in productList){

            val card = layoutInflater.inflate(
                R.layout.card_cartproduct,
                cartContainer,
                false
            )
            card.card_titleCart.text = product.name.capitalize()
            card.card_bodyCart.text = "R$ " + product.price
            card.txtQtdItens.text = product.qtd.toString() + " item(s)"

            product.qtd.let {
                total += product.price * it.toString().toInt();
            }

            val imgUrl = product.urlImg.toString()
            Picasso.get().load(imgUrl).into(card.card_headerCart)

            card.btnRemoveItem.setOnClickListener(removeItem(product, cartView))
            cartContainer.addView(card)
        }
    }

    fun removeItem(product: Product, cartView: View): View.OnClickListener? {
        return View.OnClickListener { _ ->
            Thread{
                val produtoSelecionado =
                    Product(name = product.name, desc = product.desc, price = product.price, id = product.id, qtd =product.qtd?.minus(1), urlImg = "")
                deleteProduct(produtoSelecionado)
                refreshProducts(cartView)
            }.start()

        }

    }

    fun deleteAll(cartView: View) {
        Thread{
            activity?.let {
                val db = Room.databaseBuilder(it, AppDatabase::class.java, "AppDB").build()
                val all = db.productDao().getAll()
                for (product in all){ deleteProduct(product) }
                refreshProducts(cartView)
            }
        }.start()
    }

    fun deleteProduct(product: Product){
        activity?.let {
            val db = Room.databaseBuilder(it, AppDatabase::class.java, "AppDB").build()
            db.productDao().delete(product)
        }
    }

    fun updateUserHistory(cartView: View) {
        Thread {
            activity?.let{
                val db = Room.databaseBuilder(it, AppDatabase::class.java, "AppDB").build()
                val productList = db.productDao().getAll()

                // Custom timeout
                val httpClient = OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://brebestore-ebbd1.firebaseio.com")
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val api = retrofit.create(ProductAPI::class.java)

                val auth = FirebaseAuth.getInstance()

                var count = 0
                for (product in productList) {
                    val call = api.insert(auth.currentUser?.displayName, product)

                    val callback = object: Callback<Void> {

                        override fun onResponse(call: Call<Void>, response: Response<Void>) {

                            if(response.isSuccessful) {
                                count++
                                if(count == productList.size) {
                                    alert("Parabéns!",
                                        "Sua compra foi efetuada com sucesso. A Brebe Store agradece sua preferência.", cartView.context)
                                    deleteAll(cartView)
                                }
                            } else {
                                Toast.makeText(cartView.context,
                                    "Connection error", Toast.LENGTH_LONG).show()
                                // Check issue in the API response itself
                                Log.e("API ERROR", response.errorBody().toString())
                            }

                        }

                        // Check issue in calling the API/getting a response from it
                        override fun onFailure(call: Call<Void>, t: Throwable) {

                            Toast.makeText(cartView.context,
                                "Connection error", Toast.LENGTH_LONG).show()
                            Log.e("ProductListActivity", "getAllProducts", t) // tag (Activity), msg (Method), t
                        }
                    }
                    call.enqueue(callback)
                }
            }

        }.start()
    }
}