package com.nekobitlz.products.features.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nekobitlz.products.data.repository.IProductsRepository

class ProductsViewModelFactory(
    private val groupId: Int,
    private val repository: IProductsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(Int::class.java, IProductsRepository::class.java)
            .newInstance(groupId, repository)
    }
}