package com.nekobitlz.vksharing.data

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nekobitlz.vksharing.data.commands.VKWallPostCommand
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

interface IShareRepository {
    fun sendPost(userId: Int = 0, message: String, attachment: Uri?): LiveData<Int>
}

class ShareRepository : IShareRepository {

    override fun sendPost(userId: Int, message: String, attachment: Uri?): LiveData<Int> {
        val command = VKWallPostCommand(message = message, photos = attachment, ownerId = userId)
        val data = MutableLiveData<Int>()

        VK.execute(command, object : VKApiCallback<Int> {
            override fun success(result: Int) {
                data.value = result
            }

            override fun fail(error: Exception) {
                error.printStackTrace()
                data.value = -1
            }
        })

        return data
    }
}