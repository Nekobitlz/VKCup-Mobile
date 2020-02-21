package com.nekobitlz.documentslist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

interface IDocumentsRepository {
    fun getDocuments(): LiveData<List<VKDocument>>
}

class DocumentsRepository(private val userId: Int) : IDocumentsRepository {

    override fun getDocuments(): LiveData<List<VKDocument>> {
        val documentsRequest = VkDocumentsRequest(ownerId = userId)
        val data = MutableLiveData<List<VKDocument>>()
        data.value = emptyList()

        VK.execute(documentsRequest, object : VKApiCallback<List<VKDocument>> {
            override fun success(result: List<VKDocument>) {
                data.value = result
            }

            override fun fail(error: Exception) {
                error.printStackTrace()
            }

        })

        return data
    }
}