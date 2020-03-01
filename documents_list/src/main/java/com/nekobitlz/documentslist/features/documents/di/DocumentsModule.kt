package com.nekobitlz.documentslist.features.documents.di

import com.nekobitlz.documentslist.data.repository.DocumentsRepository
import com.nekobitlz.documentslist.data.repository.IDocumentsRepository
import com.nekobitlz.documentslist.features.documents.DocumentsViewModelFactory

interface DocumentsComponent {
    val documentsRepository: IDocumentsRepository
    val documentsViewModelFactory: DocumentsViewModelFactory
}

class DocumentsModule(userId: Int) : DocumentsComponent {

    override val documentsRepository: IDocumentsRepository = DocumentsRepository(userId)

    override val documentsViewModelFactory: DocumentsViewModelFactory
        get() = DocumentsViewModelFactory(documentsRepository)
}