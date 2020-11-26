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
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.room.Room
import bt.senacbcc.brebestore.R
import bt.senacbcc.brebestore.activities.MainActivity
import bt.senacbcc.brebestore.db.AppDatabase
import bt.senacbcc.brebestore.model.Product
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
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
    lateinit var progressBarProducts: ProgressBar

    var db = FirebaseFirestore.getInstance()

    //Variavel Check Chip Categoria
    var catFem = false
    var catMasc = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val homeView = inflater.inflate(R.layout.fragment_home, container, false)

        progressBarProducts = homeView.findViewById(R.id.progressBarProducts)
        progressBarProducts.visibility = View.VISIBLE

        getProducts()

        homeView.btnSearch.setOnClickListener {
            filterProductsByName()
        }

        homeView.chip_fem.setOnCheckedChangeListener { buttonView, isChecked ->
            catFem = isChecked
            filterProductsByCategory()

        }
        homeView.chip_masc.setOnCheckedChangeListener { buttonView, isChecked ->
            catMasc = isChecked
            filterProductsByCategory()
        }

        return homeView

    }
    fun filterProductsByCategory(){

        //Limpa etBusca p nao tentar fazer dois filtros ao mesmo tempo
        etSearch.setText("")
        progressBarProducts.visibility = View.VISIBLE

        val arr: MutableList<String> = ArrayList()
        if (catMasc && catFem){
            arr.add("masculino")
            arr.add("feminino")
        }else if(catFem && !catMasc){
            arr.add("feminino")
        }
        else if(catMasc && !catFem){
            arr.add("masculino")
        }else{
            getProducts()
        }

        //Filtrar produtos
        val productsRef = db.collection("produtos")
        productContainer.removeAllViews()
        for (type in arr){
            productsRef.whereArrayContains("categoria", type).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        updateUI(document.data)
                    }
                }
                .addOnFailureListener { exception ->
                    Snackbar.make(coordinatorLayoutProduct, "Erro ao buscar produtos!", Snackbar.LENGTH_LONG).show()
                    Log.w(TAG, "Error getting documents: ", exception)
                }
        }
        progressBarProducts.visibility = View.GONE
    }

    fun filterProductsByName(){
        val busca = etSearch.text.toString()
        progressBarProducts.visibility = View.VISIBLE
        //Filtrar produtos
        val productsRef = db.collection("produtos")

        productsRef.orderBy("nome").startAt(busca).endAt(busca + "~")
            .get()
            .addOnSuccessListener { documents ->
                productContainer.removeAllViews()
                for (document in documents) {
                    updateUI(document.data)
                }
                progressBarProducts.visibility = View.GONE
            }
            .addOnFailureListener { exception ->
                Snackbar.make(coordinatorLayoutProduct, "Erro ao buscar produtos!", Snackbar.LENGTH_LONG).show()
                Log.w(TAG, "Error getting documents: ", exception)
            }

    }

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
                    progressBarProducts.visibility = View.GONE
                } else {
                    Snackbar.make(coordinatorLayoutProduct, "Erro ao encontrar produtos!", Snackbar.LENGTH_LONG).show()
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

        val imgUrl = productData["urlImg"].toString()
        Picasso.get().load(imgUrl).into(card.card_header)

        card.btnBuy.setOnClickListener(buyItem(productData))
        //card.setOnClickListener(clickInCard(productData));
        productContainer.addView(card)

    }

    //Item vai direto pro carrinho
    private fun buyItem(selectedProduct: Map<String, Any>): View.OnClickListener? {
        return View.OnClickListener { v ->
            Thread{
            val produtoSelecionado =
                Product(name = selectedProduct["nome"].toString(), desc = selectedProduct["desc"].toString(), price = selectedProduct["preco"].toString().toFloat(), qtd = 1, urlImg = selectedProduct["urlImg"].toString())
                insertProduct(produtoSelecionado)
            }.start()
        }
    }

    //Tela de detalhes do item
    private fun clickInCard(selectedProduct: Map<String, Any>): View.OnClickListener? {
        return View.OnClickListener { v ->
            val mainAct = activity as MainActivity
            mainAct.mostrarPaginaProduto(selectedProduct)
        }
    }

    fun insertProduct(product: Product){
        activity?.let{
        val roomdb = Room.databaseBuilder(it, AppDatabase::class.java, "AppDB").build()
        roomdb.productDao().insert(product)
        Snackbar.make(coordinatorLayoutProduct, "Produto adicionado com sucesso ao carrinho!", Snackbar.LENGTH_LONG).show()
        }
    }


}

