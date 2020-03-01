package com.nekobitlz.products.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nekobitlz.products.data.models.Product
import com.nekobitlz.products.data.requests.VkGetProductsRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

interface IProductsRepository {
    fun getProducts(groupId: Int): LiveData<List<Product>>
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
}