package com.nekobitlz.products.features.cities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nekobitlz.products.data.repository.ICitiesRepository

class CityViewModelFactory(
    private val citiesRepository: ICitiesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(ICitiesRepository::class.java)
            .newInstance(citiesRepository)
    }
}