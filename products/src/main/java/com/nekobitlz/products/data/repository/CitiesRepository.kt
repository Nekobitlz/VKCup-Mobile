package com.nekobitlz.products.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nekobitlz.products.data.models.City
import com.nekobitlz.products.data.requests.VkGetCitiesRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

interface ICitiesRepository {
    fun getCities(): LiveData<List<City>>
}

class CitiesRepository : ICitiesRepository {

    override fun getCities(): LiveData<List<City>> {
        val request = VkGetCitiesRequest()
        val data = MutableLiveData<List<City>>()

        VK.execute(request, object : VKApiCallback<List<City>> {
            override fun success(result: List<City>) {
                data.value = result
            }

            override fun fail(error: Exception) {
                error.printStackTrace()
            }
        })

        return data
    }

}