package bt.senacbcc.brebestore.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bt.senacbcc.brebestore.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsFragment : Fragment() {

    //private var produtoDetalhado: Map<String, Any>? = null
    private var produtoDetalhado: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            produtoDetalhado = it.getString("prd")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val detailsView = inflater.inflate(R.layout.product_page, container, false)

        return detailsView
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Map<String, Any>) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("prd", param1.toString())
                }
            }
    }
}