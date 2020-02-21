package com.nekobitlz.documentslist.features

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nekobitlz.documentslist.data.DocumentsRepository
import com.nekobitlz.documentslist.data.IDocumentsRepository
import com.nekobitlz.documentslist.data.VKDocument

class DocumentsViewModel(
    private val userId: Int
) : ViewModel() {

    val documents: LiveData<List<VKDocument>>

    init {
        val documentsRepository: IDocumentsRepository = DocumentsRepository(userId)

        documents = documentsRepository.getDocuments()
    }
}