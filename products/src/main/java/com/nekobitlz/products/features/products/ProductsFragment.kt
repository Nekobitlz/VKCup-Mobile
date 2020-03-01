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
import com.nekobitlz.products.data.models.Shop
import com.nekobitlz.products.di.injector
import com.nekobitlz.products.features.product_info.ProductInfoFragment
import com.nekobitlz.products.features.products.di.ProductsComponent
import kotlinx.android.synthetic.main.fragment_products.*

class ProductsFragment : Fragment() {

    private lateinit var shop: Shop

    private val adapter by lazy {
        ProductsAdapter(onClick)
    }
    private lateinit var viewModel: ProductsViewModel
    private lateinit var component: ProductsComponent

    private val viewModelFactory by lazy {
        component.productsViewModelFactory
    }

    private val onClick: (Product) -> Unit = {
        openProductInfoFragment(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shop = requireArguments().getSerializable(SHOP_TAG) as Shop
        component = injector.createProductsComponent(shop.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_products, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initViewModel()
        initRecyclerView()
    }

    private fun initToolbar() {
        requireActivity().setActionBar(toolbar)
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)
        requireActivity().actionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { requireActivity().supportFragmentManager.popBackStack() }
        toolbar.title = "${resources.getString(R.string.shop_products)} ${shop.name}"
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProductsViewModel::class.java)
        viewModel.getProducts().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun initRecyclerView() {
        rv_products.layoutManager = GridLayoutManager(requireContext(), 2)
        rv_products.setHasFixedSize(true)
        rv_products.adapter = adapter
    }

    private fun openProductInfoFragment(product: Product) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, ProductInfoFragment.newInstance(product))
            .addToBackStack(null)
            .commit()
    }

    companion object {

        private const val SHOP_TAG = "SHOP_TAG"

        fun getInstance(shop: Shop): ProductsFragment {
            val bundle = Bundle()
            val fragment = ProductsFragment()
            bundle.putSerializable(SHOP_TAG, shop)
            fragment.arguments = bundle

            return fragment
        }
    }
}