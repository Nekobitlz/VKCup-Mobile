package com.nekobitlz.products.features.shops

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ShopsViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor().newInstance()
    }

}