package com.nekobitlz.photohandler

import android.app.Application
import com.vk.api.sdk.VK

class PhotoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        VK.initialize(applicationContext)
    }
}