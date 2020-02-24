package com.nekobitlz.vksharing

import android.app.Application
import com.nekobitlz.vksharing.di.Injector
import com.vk.api.sdk.VK

class SharingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        VK.initialize(this)
        injector = Injector()
    }

    companion object {
        lateinit var injector: Injector
    }
}