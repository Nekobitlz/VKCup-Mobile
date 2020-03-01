package com.nekobitlz.products.features.shops.di

import com.nekobitlz.products.di.modules.ApplicationComponent
import com.nekobitlz.products.features.shops.ShopsViewModelFactory

interface ShopsComponent {
    val shopsViewModelFactory: ShopsViewModelFactory
}

class ShopsModule(private val applicationComponent: ApplicationComponent) : ShopsComponent {

    override val shopsViewModelFactory: ShopsViewModelFactory
        get() = ShopsViewModelFactory(applicationComponent.shopsRepository)
}