package com.nekobitlz.products.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nekobitlz.products.data.models.Product
import com.nekobitlz.products.data.requests.VkAddToFavouriteRequest
import com.nekobitlz.products.data.requests.VkGetProductsRequest
import com.nekobitlz.products.data.requests.VkRemoveFromFavouriteRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

interface IProductsRepository {
    fun getProducts(groupId: Int): LiveData<List<Product>>
    fun addToFavourite(groupId: Int, productId: Int): LiveData<Boolean>
    fun removeFromFavourite(groupId: Int, productId: Int): LiveData<Boolean>
}

class ProductsRepository : IProductsRepository {

    override fun getProducts(groupId: Int): LiveData<List<Product>> {
        val request = VkGetProductsRequest(groupId)
        val data = MutableLiveData<List<Product>>()

        VK.execute(request, object : VKApiCallback<List<Product>> {
            override fun success(result: List<Product>) {
                data.value = result
            }

            override fun fail(error: Exception) {
                error.printStackTrace()
            }
        })

        return data
    }

    override fun addToFavourite(groupId: Int, productId: Int): LiveData<Boolean> {
        val request = VkAddToFavouriteRequest(groupId, productId)
        val data = MutableLiveData<Boolean>()

        VK.execute(request, object : VKApiCallback<Int> {
            override fun success(result: Int) {
                data.value = result == 1
            }

            override fun fail(error: Exception) {
                error.printStackTrace()
                data.value = false
            }
        })

        return data
    }

    override fun removeFromFavourite(groupId: Int, productId: Int): LiveData<Boolean> {
        val request = VkRemoveFromFavouriteRequest(groupId, productId)
        val data = MutableLiveData<Boolean>()

        VK.execute(request, object : VKApiCallback<Int> {
            override fun success(result: Int) {
                data.value = result == 1
            }

            override fun fail(error: Exception) {
                error.printStackTrace()
                data.value = false
            }
        })

        return data
    }
}