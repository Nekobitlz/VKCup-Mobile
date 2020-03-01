package com.nekobitlz.photohandler.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nekobitlz.photohandler.data.models.Album
import com.nekobitlz.photohandler.data.requests.VkGetAlbumsRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

interface IAlbumsRepository {
    fun getAlbums(): LiveData<List<Album>>
}

class AlbumsRepository : IAlbumsRepository {

    override fun getAlbums(): LiveData<List<Album>> {
        val request = VkGetAlbumsRequest()
        val data = MutableLiveData<List<Album>>()

        VK.execute(request, object : VKApiCallback<List<Album>> {
            override fun success(result: List<Album>) {
                data.value = result
            }

            override fun fail(error: Exception) {
                error.printStackTrace()
            }
        })

        return data
    }
}