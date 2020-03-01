package com.nekobitlz.documentslist.features.documents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nekobitlz.documentslist.data.repository.IDocumentsRepository

class DocumentsViewModelFactory(
    private val documentsRepository: IDocumentsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(IDocumentsRepository::class.java)
            .newInstance(documentsRepository)
    }
}