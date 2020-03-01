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
import com.nekobitlz.products.data.models.City
import com.nekobitlz.products.data.models.Shop
import com.nekobitlz.products.features.cities.CityFragment
import com.nekobitlz.products.features.listeners.OnCheckedListener
import kotlinx.android.synthetic.main.fragment_shops.*

class ShopsFragment : Fragment(), OnCheckedListener {

    private var toolbarText: String? = null

    private val adapter: ShopsAdapter by lazy {
        ShopsAdapter(onClick)
    }

    private lateinit var viewModel: ShopsViewModel

    private val viewModelFactory: ShopsViewModelFactory by lazy {
        ShopsViewModelFactory()
    }

    private val onClick: (Shop) -> Unit = {
        //TODO()
    }

    override fun onChecked(city: City) {
        toolbarText = "${resources.getString(R.string.shops_toolbar_title)} ${city.name}"
        toolbar.title = toolbarText

        viewModel.onChecked(city)
        observeShops()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_shops, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initRecyclerView()
        initViewModel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TOOLBAR_TITLE_TAG, toolbarText)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            toolbarText = savedInstanceState.getString(TOOLBAR_TITLE_TAG)!!
        }
    }

    private fun initToolbar() {
        val fragment = CityFragment()
        fragment.setTargetFragment(this, 0)

        if (toolbarText == null) {
            toolbarText = "${resources.getString(R.string.shops_toolbar_title)} " +
                    resources.getString(R.string.shops_toolbar_country_name)
        }

        toolbar.title = toolbarText
        toolbar.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun initRecyclerView() {
        rv_shops.adapter = adapter
        rv_shops.setHasFixedSize(true)
        rv_shops.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(ShopsViewModel::class.java)
        observeShops()
    }

    private fun observeShops() {
        viewModel.getShops().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    companion object {
        private const val TOOLBAR_TITLE_TAG = "TOOLBAR_TITLE_TAG"
    }
}