package com.nekobitlz.products.features.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nekobitlz.products.data.models.Product
import com.nekobitlz.products.data.repository.IProductsRepository

class ProductsViewModel(private val groupId: Int, private val repository: IProductsRepository) :
    ViewModel() {

    private var products = repository.getProducts(groupId)
    lateinit var isFavourite: LiveData<Boolean>

    fun getProducts(): LiveData<List<Product>> {
        val newProducts = repository.getProducts(groupId) as MutableLiveData
        newProducts.value = products.value
        products = newProducts

        return products
    }

    fun addToFavourite(product: Product) {
        isFavourite = repository.addToFavourite(groupId, product.id)
    }

    fun removeFromFavourite(product: Product) {
        isFavourite = repository.removeFromFavourite(groupId, product.id)
    }
}