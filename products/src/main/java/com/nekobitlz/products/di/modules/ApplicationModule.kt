package com.nekobitlz.products.di.modules

import com.nekobitlz.products.data.repository.*

interface ApplicationComponent {
    val shopsRepository: IShopsRepository
    val citiesRepository: ICitiesRepository
    val productsRepository: IProductsRepository
}

class ApplicationModule : ApplicationComponent {
    override val shopsRepository: IShopsRepository = ShopsRepository()
    override val citiesRepository: ICitiesRepository = CitiesRepository()
    override val productsRepository: IProductsRepository = ProductsRepository()
}