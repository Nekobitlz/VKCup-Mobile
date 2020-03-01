package com.nekobitlz.products.features.shops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nekobitlz.products.data.repository.IShopsRepository

class ShopsViewModelFactory(private val shopsRepository: IShopsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(IShopsRepository::class.java)
            .newInstance(shopsRepository)
    }

}