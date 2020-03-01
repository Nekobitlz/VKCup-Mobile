package com.nekobitlz.products.features.cities

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nekobitlz.products.data.models.City
import com.nekobitlz.products.data.repository.CitiesRepository
import com.nekobitlz.products.data.repository.ICitiesRepository

class CityViewModel(private val repository: ICitiesRepository = CitiesRepository()) : ViewModel() {

    val cities: LiveData<List<City>> by lazy {
        repository.getCities()
    }
}