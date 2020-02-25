package com.nekobitlz.unsubscribe.data

import android.util.Log
import com.nekobitlz.unsubscribe.data.models.Group
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nekobitlz.unsubscribe.data.requests.VkGroupsGetRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

interface IGroupsRepository {
    fun getGroupList(): LiveData<List<Group>>

}

class GroupsRepository : IGroupsRepository {

    override fun getGroupList(): LiveData<List<Group>> {
        val request = VkGroupsGetRequest()
        val data = MutableLiveData<List<Group>>()
        data.value = emptyList()

        VK.execute(request, object : VKApiCallback<List<Group>> {
            override fun success(result: List<Group>) {
                Log.e("VK", "success ${result.size}")
                data.value = result
            }

            override fun fail(error: Exception) {
                Log.e("VK", error.message)
                error.printStackTrace()
            }
        })

        return data
    }

}