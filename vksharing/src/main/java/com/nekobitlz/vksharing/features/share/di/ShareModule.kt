package com.nekobitlz.vksharing.features.share.di

import com.nekobitlz.vksharing.data.IShareRepository
import com.nekobitlz.vksharing.data.ShareRepository
import com.nekobitlz.vksharing.features.share.ShareViewModelFactory

interface ShareComponent {
    val shareRepository: IShareRepository
    val shareViewModelFactory: ShareViewModelFactory
}

class ShareModule : ShareComponent {
    override val shareRepository: IShareRepository = ShareRepository()

    override val shareViewModelFactory: ShareViewModelFactory
        get() = ShareViewModelFactory(shareRepository)
}