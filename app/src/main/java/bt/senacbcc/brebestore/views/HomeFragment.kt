package bt.senacbcc.brebestore.views

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.room.Room
import bt.senacbcc.brebestore.R
import bt.senacbcc.brebestore.db.AppDatabase
import bt.senacbcc.brebestore.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.card_product.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.io.InputStream
import java.net.URL


class HomeFragment : Fragment() {

    private var mStorageRef: StorageReference? = null

    companion object {
        private const val TAG = "HomeFragment"
    }
    lateinit var storage: FirebaseStorage
    var db = FirebaseFirestore.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val homeView = inflater.inflate(R.layout.fragment_home, container, false)

        getProducts()

        homeView.btnSearch.setOnClickListener {
            filterProductsByName()
        }

        homeView.chip_fem.setOnCheckedChangeListener { buttonView, isChecked ->
            filterProductsByCategory("feminino")
        }
        homeView.chip_masc.setOnCheckedChangeListener { buttonView, isChecked ->
            filterProductsByCategory("masculino")
        }

        return homeView

    }

    fun filterProductsByCategory(type: String){
        //Filtrar produtos
        val productsRef = db.collection("produtos")

        productsRef.whereArrayContains("categoria", type).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    productContainer.removeAllViews()
                    updateUI(document.data)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    fun filterProductsByName(){
        val busca = etSearch.text

        //Filtrar produtos
        val productsRef = db.collection("produtos")

        productsRef.whereEqualTo("nome", busca.toString()).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    productContainer.removeAllViews()
                    updateUI(document.data)

                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

        /*productsRef
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {

                        val card = layoutInflater.inflate(
                            R.layout.card_product,
                            productContainer,
                            false
                        )

                        val productData = document.data
                        card.card_title.text = productData["nome"].toString()
                        card.card_body.text = "R$ " + productData["preco"].toString()

                        val imgStream = productData["urlImg"].toString()

                        productContainer.addView(card)
                        Log.d(TAG, document.id + " => " + document.data)
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }*/
    }

    //GetProducts from Storage
    /*    fun getProducts(){

        var storageRef = Firebase.storage.reference

        // imagesRef now points to "images"
        var imagesRef: StorageReference? = storageRef.child("produtos")

        imagesRef?.listAll()
                ?.addOnSuccessListener { (items) ->
                items.forEach { item ->
                    val card = layoutInflater.inflate(R.layout.card_product, productContainer, false)

                    card.card_title.text = item.name
                    item.getStream { state, stream ->
                        card.card_header.setImageBitmap(BitmapFactory.decodeStream(stream))
                    }
                    productContainer.addView(card)
                }
            }
            ?.addOnFailureListener {
                // Uh-oh, an error occurred!
            }

        }*/

    //GetProducts from FireStore
    fun getProducts(){
        val productsRef = db.collection("produtos")

        productsRef
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {

                        updateUI(document.data)

                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }
    }

    fun updateUI(productMap: Map<String, Any>){

        val productData = productMap
        val card = layoutInflater.inflate(
            R.layout.card_product,
            productContainer,
            false
        )
        card.card_title.text = productData["nome"].toString()
        card.card_body.text = "R$ " + productData["preco"].toString()

        val imgStream = productData["urlImg"].toString()

        card.btnBuy.setOnClickListener(buyItem(productData))
        card.setOnClickListener(clickInCard(productData));
        productContainer.addView(card)

    }

    //Item vai direto pro carrinho
    private fun buyItem(selectedProduct: Map<String, Any>): View.OnClickListener? {
        return View.OnClickListener { v ->
            Thread{

            val produtoSelecionado =
                Product(name = selectedProduct["nome"].toString(), desc = selectedProduct["desc"].toString(), price = selectedProduct["preco"].toString().toFloat(), qtd = 1, urlImg = "", id = 6)
                insertProduct(produtoSelecionado)
        }.start()
        }
    }

    //Tela de detalhes do item
    private fun clickInCard(selectedProduct: Map<String, Any>): View.OnClickListener? {
        return View.OnClickListener { v ->
            val frag = DetailsFragment()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.productContainer, frag)?.commit()
            //val i = Intent(activity, DetailsFragment::class.java)
            //i.putExtra("produto", selectedProduct)
            //startActivity(i)
        }
    }

    fun insertProduct(product: Product){
        activity?.let{
        val roomdb = Room.databaseBuilder(it, AppDatabase::class.java, "AppDB").build()
        roomdb.productDao().insert(product)
        }
    }


}

