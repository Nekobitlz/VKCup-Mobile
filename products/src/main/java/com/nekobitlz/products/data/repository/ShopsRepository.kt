package com.nekobitlz.products.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nekobitlz.products.data.models.Shop
import com.nekobitlz.products.data.requests.VkGetShopsRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

interface IShopsRepository {
    fun getShops(): LiveData<List<Shop>>
}

class ShopsRepository : IShopsRepository {

    override fun getShops(): LiveData<List<Shop>> {
        val request = VkGetShopsRequest()
        val data = MutableLiveData<List<Shop>>()

        VK.execute(request, object : VKApiCallback<List<Shop>> {
            override fun success(result: List<Shop>) {
                Log.e("VK", "success ${result.size}")
                data.value = result
            }

            override fun fail(error: Exception) {
                Log.e("VK", "error ${error.message}")
                error.printStackTrace()
            }
        })

        return data
    }
}