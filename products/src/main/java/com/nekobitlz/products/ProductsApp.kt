package com.nekobitlz.products

import android.app.Application
import com.nekobitlz.products.di.Injector
import com.vk.api.sdk.VK

class ProductsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        VK.initialize(applicationContext)
        injector = Injector()
    }

    companion object{
        lateinit var injector: Injector
    }
}