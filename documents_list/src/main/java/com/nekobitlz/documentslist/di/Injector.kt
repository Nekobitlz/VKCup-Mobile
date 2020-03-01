package com.nekobitlz.documentslist.di

import com.nekobitlz.documentslist.DocumentsApp
import com.nekobitlz.documentslist.features.documents.di.DocumentsComponent
import com.nekobitlz.documentslist.features.documents.di.DocumentsModule

class Injector {

    fun getDocumentsModule(userId: Int): DocumentsComponent = DocumentsModule(userId)
}

val injector: Injector get() = DocumentsApp.injector