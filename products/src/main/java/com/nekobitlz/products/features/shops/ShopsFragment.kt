package com.nekobitlz.products.features.shops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nekobitlz.products.R
import kotlinx.android.synthetic.main.fragment_shops.*

class ShopsFragment : Fragment() {

    private val adapter: ShopsAdapter by lazy {
        ShopsAdapter()
    }

    private lateinit var viewModel: ShopsViewModel

    private val viewModelFactory: ShopsViewModelFactory by lazy {
        ShopsViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_shops, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        viewModel = ViewModelProvider(this, viewModelFactory).get(ShopsViewModel::class.java)
        viewModel.getShops().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun initRecyclerView() {
        rv_shops.adapter = adapter
        rv_shops.setHasFixedSize(true)
        rv_shops.layoutManager = LinearLayoutManager(requireContext())
    }
}