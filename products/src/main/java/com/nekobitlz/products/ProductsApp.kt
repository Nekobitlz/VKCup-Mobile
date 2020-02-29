package com.nekobitlz.products

import android.app.Application
import com.vk.api.sdk.VK

class ProductsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        VK.initialize(applicationContext)
    }
}