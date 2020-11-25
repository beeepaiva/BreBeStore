package bt.senacbcc.brebestore.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import bt.senacbcc.brebestore.R
import com.bumptech.glide.Glide
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.card_product.view.*
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private var mStorageRef: StorageReference? = null

    companion object {
        private const val TAG = "KotlinActivity"
    }
    lateinit var storage: FirebaseStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val homeView = inflater.inflate(R.layout.fragment_home, container, false)

        // Write a message to the database
        /*val database = Firebase.database
        val myRef = database.getReference("teste")*/

        //myRef.setValue("Hello, World!")

        // Read from the database
       /* myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>()
                Log.d(TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })*/


        //FIREBASE STORAFGE IMAGENS PRODUTOS
        storage = Firebase.storage

        getProducts()


        return homeView
    }

    fun getProducts(){

        var storageRef = storage.reference

        // imagesRef now points to "images"
        var imagesRef: StorageReference? = storageRef.child("produtos")

        val storageReference = Firebase.storage.reference

        imagesRef?.listAll()
                ?.addOnSuccessListener { (items, prefixes) ->
            prefixes.forEach { prefix ->
                // All the prefixes under listRef.
                // You may call listAll() recursively on them.
                val test = prefix.name
                val oi = prefix

            }

            items.forEach { item ->
                val card =
                    layoutInflater.inflate(R.layout.card_product, productContainer, false)
                    card.card_title.text = item.name

                item.getBytes(Long.MAX_VALUE).addOnSuccessListener {

                }.addOnFailureListener {
                    // Handle any errors
                }

                Glide.with(this /* context */)
                    .load(item)
                    .into(card.card_header)


                productContainer.addView(card)
            }
                }
            ?.addOnFailureListener {
                // Uh-oh, an error occurred!
            }

        }

    }

