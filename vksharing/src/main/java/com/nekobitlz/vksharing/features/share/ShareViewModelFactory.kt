package com.nekobitlz.vksharing.features.share

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ShareViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor()
            .newInstance()
    }
}