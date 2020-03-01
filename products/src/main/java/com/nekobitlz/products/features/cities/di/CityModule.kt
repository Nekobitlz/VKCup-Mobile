package com.nekobitlz.products.features.cities.di

import com.nekobitlz.products.di.modules.ApplicationComponent
import com.nekobitlz.products.features.cities.CityViewModelFactory

interface CityComponent {
    val cityViewModelFactory: CityViewModelFactory
}

class CityModule(private val applicationComponent: ApplicationComponent) : CityComponent {
    override val cityViewModelFactory: CityViewModelFactory
        get() = CityViewModelFactory(applicationComponent.citiesRepository)
}