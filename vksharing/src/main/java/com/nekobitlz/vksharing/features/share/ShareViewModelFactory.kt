package com.nekobitlz.vksharing.features.share

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nekobitlz.vksharing.data.IShareRepository

class ShareViewModelFactory(private val shareRepository: IShareRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(IShareRepository::class.java)
            .newInstance(shareRepository)
    }
}