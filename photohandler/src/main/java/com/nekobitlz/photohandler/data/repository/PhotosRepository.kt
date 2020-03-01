package com.nekobitlz.photohandler.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nekobitlz.photohandler.data.models.Photo
import com.nekobitlz.photohandler.data.requests.VkGetPhotosRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

interface IPhotosRepository {
    fun getPhotos(albumId: Int): LiveData<List<Photo>>
}

class PhotosRepository : IPhotosRepository {
    override fun getPhotos(albumId: Int): LiveData<List<Photo>> {
        val request = VkGetPhotosRequest(albumId)
        val data = MutableLiveData<List<Photo>>()

        VK.execute(request, object : VKApiCallback<List<Photo>> {
            override fun success(result: List<Photo>) {
                data.value = result
            }

            override fun fail(error: Exception) {
                error.printStackTrace()
            }
        })

        return data
    }
}