package com.nekobitlz.photohandler.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nekobitlz.photohandler.data.models.Photo
import com.nekobitlz.photohandler.data.requests.VkAddPhotoRequest
import com.nekobitlz.photohandler.data.requests.VkGetPhotosRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

interface IPhotosRepository {
    fun getPhotos(albumId: Int): LiveData<List<Photo>>
    fun addPhoto(albumId: Int, photo: Uri): LiveData<Boolean>
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

    override fun addPhoto(albumId: Int, photo: Uri): LiveData<Boolean> {
        val request = VkAddPhotoRequest(albumId, photo)
        val data = MutableLiveData<Boolean>()

        VK.execute(request, object : VKApiCallback<Int> {
            override fun success(result: Int) {
                data.value = result > 0
            }

            override fun fail(error: Exception) {
                error.printStackTrace()
                data.value = false
            }
        })

        return data
    }
}