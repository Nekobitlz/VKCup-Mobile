package com.nekobitlz.documentslist

import android.app.Application
import com.nekobitlz.documentslist.di.Injector
import com.vk.api.sdk.VK

class DocumentsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        VK.initialize(applicationContext)
        injector = Injector()
    }

    companion object {
        lateinit var injector: Injector
    }
}