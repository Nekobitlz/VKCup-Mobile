package com.nekobitlz.documentslist.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DocumentsViewModelFactory(
    private val userId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Int::class.java)
            .newInstance(userId)
    }
}