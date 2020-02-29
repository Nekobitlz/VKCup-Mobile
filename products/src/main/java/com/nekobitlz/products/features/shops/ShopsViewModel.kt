package com.nekobitlz.products.features.shops

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nekobitlz.products.data.models.Shop
import com.nekobitlz.products.data.repository.IShopsRepository
import com.nekobitlz.products.data.repository.ShopsRepository

class ShopsViewModel(private val shopsRepository: IShopsRepository = ShopsRepository()) :
    ViewModel() {

    private val shops: MutableLiveData<List<Shop>> = shopsRepository.getShops() as MutableLiveData<List<Shop>>

    fun getShops(): LiveData<List<Shop>> = shops
}