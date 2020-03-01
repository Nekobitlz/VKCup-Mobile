package com.nekobitlz.documentslist.features.documents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nekobitlz.documentslist.data.repository.IDocumentsRepository
import com.nekobitlz.documentslist.data.models.VKDocument

class DocumentsViewModel(
    private val documentsRepository: IDocumentsRepository
) : ViewModel() {

    lateinit var dialogEvent: LiveData<Int>
    var docs: LiveData<List<VKDocument>>

    init {
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