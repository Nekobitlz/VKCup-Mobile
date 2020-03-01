package com.nekobitlz.products.features.product_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nekobitlz.products.R
import com.nekobitlz.products.data.models.Product
import com.nekobitlz.products.di.injector
import com.nekobitlz.products.features.products.ProductsViewModel
import com.nekobitlz.products.features.products.di.ProductsComponent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_product_info.*

class ProductInfoFragment : Fragment(), ProductsComponent by injector.productsComponent {

    private lateinit var product: Product
    private lateinit var viewModel: ProductsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        product = requireArguments().getSerializable(PRODUCT_TAG) as Product
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_product_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initViewModel()
        initViews()
    }

    private fun initToolbar() {
        requireActivity().setActionBar(toolbar)
        requireActivity().actionBar?.setDisplayHomeAsUpEnabled(true)
        requireActivity().actionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { requireActivity().supportFragmentManager.popBackStack() }
        toolbar.title = product.name
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, productsViewModelFactory).get(ProductsViewModel::class.java)
    }

    private fun initViews() {
        tv_product_info_name.text = product.name
        tv_product_info_price.text = product.price
        tv_product_info_description.text = product.description
        btn_favourite.setOnClickListener {
            if (product.isFavourite) {
                viewModel.removeFromFavourite(product)
            } else {
                viewModel.addToFavourite(product)
            }
            observeFavouriteStatus()
        }

        loadImageFromUrl(product.avatar, iv_product_info_avatar)
    }

    private fun observeFavouriteStatus() {
        viewModel.isFavourite.observe(viewLifecycleOwner, Observer {
            if (it) {
                if (product.isFavourite) {
                    product.isFavourite = false
                    btn_favourite.background = resources.getDrawable(R.drawable.rounded_button)
                    btn_favourite.text = resources.getString(R.string.btn_add_to_favourites)
                    btn_favourite.setTextColor(resources.getColor(R.color.colorButtonText))
                    btn_favourite.setOnClickListener {
                        viewModel.addToFavourite(product)
                    }
                } else {
                    product.isFavourite = true
                    btn_favourite.background =
                        resources.getDrawable(R.drawable.rounded_button_favourite)
                    btn_favourite.text = resources.getString(R.string.btn_delete_from_favourites)
                    btn_favourite.setTextColor(resources.getColor(R.color.colorButtonFavouriteText))
                    btn_favourite.setOnClickListener {
                        viewModel.removeFromFavourite(product)
                    }
                }
            } else {
                Toast.makeText(requireContext(), R.string.toast_error, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadImageFromUrl(url: String, imageView: ImageView) {
        Picasso
            .get()
            .load(url)
            .fit()
            .centerCrop()
            .into(imageView)
    }

    companion object {
        private const val PRODUCT_TAG = "PRODUCT_TAG"

        fun newInstance(product: Product): ProductInfoFragment {
            val bundle = Bundle()
            bundle.putSerializable(PRODUCT_TAG, product)
            val fragment = ProductInfoFragment()
            fragment.arguments = bundle

            return fragment
        }
    }
}