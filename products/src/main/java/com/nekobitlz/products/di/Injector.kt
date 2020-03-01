package com.nekobitlz.products.di

import com.nekobitlz.products.ProductsApp
import com.nekobitlz.products.di.modules.ApplicationComponent
import com.nekobitlz.products.di.modules.ApplicationModule
import com.nekobitlz.products.features.cities.di.CityComponent
import com.nekobitlz.products.features.cities.di.CityModule
import com.nekobitlz.products.features.products.di.ProductsComponent
import com.nekobitlz.products.features.products.di.ProductsModule
import com.nekobitlz.products.features.shops.di.ShopsComponent
import com.nekobitlz.products.features.shops.di.ShopsModule

class Injector {

    private val applicationComponent: ApplicationComponent = ApplicationModule()
    val shopsComponent: ShopsComponent = ShopsModule(applicationComponent)
    val cityComponent: CityComponent = CityModule(applicationComponent)
    lateinit var productsComponent: ProductsComponent

    fun createProductsComponent(id: Int): ProductsComponent {
        productsComponent = ProductsModule(id, applicationComponent)
        return productsComponent
    }
}

val injector: Injector get() = ProductsApp.injector