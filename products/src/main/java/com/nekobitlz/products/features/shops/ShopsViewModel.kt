package com.nekobitlz.products.features.shops

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nekobitlz.products.data.models.City
import com.nekobitlz.products.data.models.Shop
import com.nekobitlz.products.data.repository.IShopsRepository

class ShopsViewModel(private val shopsRepository: IShopsRepository) : ViewModel() {

    private var shops: MutableLiveData<List<Shop>> = shopsRepository.getShops() as MutableLiveData

    fun getShops(): LiveData<List<Shop>> = shops

    fun onChecked(city: City) {
        val newShops = shopsRepository.getShops(city) as MutableLiveData
        newShops.value = shops.value
        shops = newShops
    }
}