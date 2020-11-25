package bt.senacbcc.brebestore.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import bt.senacbcc.brebestore.R
import kotlinx.android.synthetic.main.fragment_home.*

class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val detailsView = inflater.inflate(R.layout.product_page, container, false)

        return detailsView
    }
}