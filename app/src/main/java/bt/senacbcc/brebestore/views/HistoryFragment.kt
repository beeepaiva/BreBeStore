package bt.senacbcc.brebestore.views

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
import kotlinx.android.synthetic.main.card_product_history.view.*
import kotlinx.android.synthetic.main.fragment_history.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.NumberFormat
import java.util.concurrent.TimeUnit

class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val historyView = inflater.inflate(R.layout.fragment_history, container, false)

        getUserHistory(historyView)

        return historyView
    }

    fun getUserHistory(historyView: View) {
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

        val call = api.getAll(auth.currentUser?.displayName)

        val callback = object: Callback<List<Product>> {

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {

                if(response.isSuccessful) {
                    updateScreen(response.body())
                }
                else {
                    Toast.makeText(historyView.context,
                        "Connection error", Toast.LENGTH_LONG).show()
                    // Check issue in the API response itself
                    Log.e("API ERROR", response.errorBody().toString())
                }

            }

            // Check issue in calling the API/getting a response from it
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {

                Toast.makeText(historyView.context,
                    "Connection error", Toast.LENGTH_LONG).show()
                Log.e("MainActivity", "getUserHistory", t) // tag (Activity), msg (Method), t
            }
        }
        call.enqueue(callback)
    }

    fun updateScreen(products: List<Product>?) {

        /* We clear and load all cards each time we update the screen. With a large number of
        entries this can lead to poor performance of the application. This issue is solved by the
        recycle view. */
        historyContainer.removeAllViews()

        val formatter = NumberFormat.getCurrencyInstance()

        products?.let {

            for(product in it) { // For each (Java)
                val card = layoutInflater.inflate(R.layout.card_product_history, historyContainer, false)

                card.txtTitle.text = product.name
                card.txtPrice.text = formatter.format(product.price)
                card.txtDesc.text = product.desc

                historyContainer.addView(card)
            }
        }
    }
}