package com.nekobitlz.vksharing.di

import com.nekobitlz.vksharing.SharingApp
import com.nekobitlz.vksharing.features.share.di.ShareComponent
import com.nekobitlz.vksharing.features.share.di.ShareModule

class Injector {
    val shareModule: ShareComponent = ShareModule()
}

val injector: Injector get() = SharingApp.injector