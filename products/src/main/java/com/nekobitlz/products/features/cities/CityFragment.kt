package com.nekobitlz.products.features.cities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nekobitlz.products.R
import com.nekobitlz.products.di.injector
import com.nekobitlz.products.features.cities.di.CityComponent
import com.nekobitlz.products.features.listeners.OnCheckedListener
import kotlinx.android.synthetic.main.fragment_cities.*

class CityFragment : Fragment(), CityComponent by injector.cityComponent {

    private val adapter: CityAdapter by lazy {
        CityAdapter(listener)
    }

    private lateinit var viewModel: CityViewModel

    private lateinit var listener: OnCheckedListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = targetFragment as OnCheckedListener
        } catch (e: ClassCastException) {
            throw ClassCastException(parentFragment.toString() + " must implement OnCheckedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_cities, container, false)

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
        toolbar.title = resources.getString(R.string.city_title)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, cityViewModelFactory).get(CityViewModel::class.java)
        viewModel.cities.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun initRecyclerView() {
        rv_cities.adapter = adapter
        rv_cities.setHasFixedSize(true)
        rv_cities.layoutManager = LinearLayoutManager(requireContext())
    }
}