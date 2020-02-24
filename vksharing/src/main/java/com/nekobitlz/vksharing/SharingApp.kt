package com.nekobitlz.vksharing

import android.app.Application
import com.vk.api.sdk.VK

class SharingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        VK.initialize(this)
    }
}