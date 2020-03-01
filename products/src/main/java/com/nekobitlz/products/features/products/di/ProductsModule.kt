package com.nekobitlz.products.features.products.di

import com.nekobitlz.products.di.modules.ApplicationComponent
import com.nekobitlz.products.features.products.ProductsViewModelFactory

interface ProductsComponent {
    val productsViewModelFactory: ProductsViewModelFactory
}

class ProductsModule(
    private val groupId: Int,
    private val applicationComponent: ApplicationComponent
) : ProductsComponent {

    override val productsViewModelFactory: ProductsViewModelFactory
        get() = ProductsViewModelFactory(groupId, applicationComponent.productsRepository)
}