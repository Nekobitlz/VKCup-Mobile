package com.nekobitlz.products.features.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CityViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor().newInstance()
    }
}