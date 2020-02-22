package com.nekobitlz.documentslist.features

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nekobitlz.documentslist.data.DocumentsRepository
import com.nekobitlz.documentslist.data.IDocumentsRepository
import com.nekobitlz.documentslist.data.VKDocument

class DocumentsViewModel(
    userId: Int
) : ViewModel() {

    val documentsRepository: IDocumentsRepository
    lateinit var dialogEvent: LiveData<Int>
    var docs: LiveData<List<VKDocument>>

    init {
        documentsRepository = DocumentsRepository(userId)
        docs = documentsRepository.getDocuments()
    }

    fun getDocuments(): LiveData<List<VKDocument>> {
        val newDocs = documentsRepository.getDocuments() as MutableLiveData
        newDocs.value = docs.value
        docs = newDocs
        return docs
    }

    fun onRenameClicked(document: VKDocument, newName: String) {
        dialogEvent = documentsRepository.rename(document, newName)
    }

    fun onDeleteClicked(document: VKDocument) {
        dialogEvent = documentsRepository.delete(document)
    }
}