package com.nekobitlz.unsubscribe.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nekobitlz.unsubscribe.data.models.Group
import com.nekobitlz.unsubscribe.data.requests.VkGroupFriendsCountRequest
import com.nekobitlz.unsubscribe.data.requests.VkGroupLeaveRequest
import com.nekobitlz.unsubscribe.data.requests.VkGroupsGetRequest
import com.nekobitlz.unsubscribe.data.requests.VkWallLastDateRequest
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback

interface IGroupsRepository {
    fun getGroupList(): LiveData<List<Group>>
    fun unsubscribe(checkedGroups: List<Group>): LiveData<Pair<Int, String>>
    fun getFriendsCount(group: Group): LiveData<Int>
    fun getLastPostDate(group: Group): LiveData<Long>
}

class GroupsRepository : IGroupsRepository {

    override fun getGroupList(): LiveData<List<Group>> {
        val request = VkGroupsGetRequest()
        val data = MutableLiveData<List<Group>>()
        data.value = emptyList()

        VK.execute(request, object : VKApiCallback<List<Group>> {
            override fun success(result: List<Group>) {
                data.value = result
            }

            override fun fail(error: Exception) {
                error.printStackTrace()
            }
        })

        return data
    }

    override fun unsubscribe(checkedGroups: List<Group>): LiveData<Pair<Int, String>> {
        val data = MutableLiveData<Pair<Int, String>>()

        for (group in checkedGroups) {
            val request = VkGroupLeaveRequest(group.id)
            VK.execute(request, object : VKApiCallback<Int> {
                override fun success(result: Int) {
                    data.value = Pair(result, group.name)
                }

                override fun fail(error: Exception) {
                    data.value = Pair(0, group.name)
                }
            })
        }

        return data
    }

    override fun getFriendsCount(group: Group): LiveData<Int> {
        val request = VkGroupFriendsCountRequest(group.id)
        val data = MutableLiveData<Int>()

        VK.execute(request, object : VKApiCallback<Int> {
            override fun success(result: Int) {
                group.friends = result
                data.value = result
            }

            override fun fail(error: Exception) {
                error.printStackTrace()
            }
        })

        return data
    }

    override fun getLastPostDate(group: Group): LiveData<Long> {
        val request = VkWallLastDateRequest(group.id)
        val data = MutableLiveData<Long>()

        VK.execute(request, object : VKApiCallback<Long> {
            override fun success(result: Long) {
                group.lastPost = result
                data.value = result
            }

            override fun fail(error: Exception) {
                error.printStackTrace()
            }
        })

        return data
    }
}