package com.nekobitlz.documentslist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nekobitlz.documentslist.data.requests.VkDeleteDocumentRequest
import com.nekobitlz.documentslist.data.requests.VkGetDocumentsRequest
import com.nekobitlz.documentslist.data.requests.VkRenameDocumentRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

interface IDocumentsRepository {
    fun getDocuments(): LiveData<List<VKDocument>>
    fun rename(document: VKDocument, newName: String): LiveData<Int>
    fun delete(document: VKDocument): LiveData<Int>
}

class DocumentsRepository(private val userId: Int) : IDocumentsRepository {

    override fun getDocuments(): LiveData<List<VKDocument>> {
        val documentsRequest = VkGetDocumentsRequest(ownerId = userId)
        val data = MutableLiveData<List<VKDocument>>()

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

    override fun rename(document: VKDocument, newName: String): LiveData<Int> {
        val renameDocumentRequest = VkRenameDocumentRequest(userId, document.id, newName)
        val data = MutableLiveData<Int>()

        VK.execute(renameDocumentRequest, object : VKApiCallback<Int> {
            override fun success(result: Int) {
                data.value = result
            }

            override fun fail(error: Exception) {
                error.printStackTrace()
            }
        })

        return data
    }

    override fun delete(document: VKDocument): LiveData<Int> {

        val deleteDocumentRequest = VkDeleteDocumentRequest(userId, document.id)
        val data = MutableLiveData<Int>()

        VK.execute(deleteDocumentRequest, object : VKApiCallback<Int> {
            override fun success(result: Int) {
                data.value = result
            }

            override fun fail(error: Exception) {
                error.printStackTrace()
            }
        })

        return data
    }
}