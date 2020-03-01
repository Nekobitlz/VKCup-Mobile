package com.nekobitlz.products.features.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.nekobitlz.products.R
import com.nekobitlz.products.data.models.Product
import kotlinx.android.synthetic.main.fragment_products.*

class ProductsFragment : Fragment() {

    private var groupId: Int = 0

    private val adapter by lazy {
        ProductsAdapter(onClick)
    }
    private lateinit var viewModel: ProductsViewModel
    private val viewModelFactory by lazy {
        ProductsViewModelFactory(groupId)
    }

    private val onClick: (Product) -> Unit = {
        openProductInfoFragment(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        groupId = requireArguments().getInt(GROUP_ID_TAG)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_products, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ProductsViewModel::class.java)
        viewModel.getProducts().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        rv_products.layoutManager = GridLayoutManager(requireContext(), 2)
        rv_products.setHasFixedSize(true)
        rv_products.adapter = adapter
    }

    private fun openProductInfoFragment(product: Product) {
        // TODO()
    }

    companion object {

        private const val GROUP_ID_TAG = "GROUP_ID_TAG"

        fun getInstance(groupId: Int): ProductsFragment {
            val bundle = Bundle()
            val fragment = ProductsFragment()
            bundle.putInt(GROUP_ID_TAG, groupId)
            fragment.arguments = bundle

            return fragment
        }
    }
}