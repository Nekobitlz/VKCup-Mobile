package com.nekobitlz.unsubscribe

import android.app.Application
import com.nekobitlz.unsubscribe.di.Injector
import com.vk.api.sdk.VK

class UnsubscribeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        VK.initialize(applicationContext)
        injector = Injector()
    }

    companion object{
        lateinit var injector: Injector
    }
}