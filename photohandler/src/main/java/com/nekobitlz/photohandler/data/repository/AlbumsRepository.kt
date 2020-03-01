package com.nekobitlz.photohandler.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nekobitlz.photohandler.data.models.Album
import com.nekobitlz.photohandler.data.requests.VkAddAlbumRequest
import com.nekobitlz.photohandler.data.requests.VkGetAlbumsRequest
import com.nekobitlz.photohandler.data.requests.VkRemoveAlbumRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

interface IAlbumsRepository {
    fun getAlbums(): LiveData<List<Album>>
    fun createAlbum(): LiveData<Boolean>
    fun removeAlbum(albumId: Int): LiveData<Boolean>
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

    override fun createAlbum(): LiveData<Boolean> {
        val request = VkAddAlbumRequest()
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

    override fun removeAlbum(albumId: Int): LiveData<Boolean> {
        val request = VkRemoveAlbumRequest(albumId)
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