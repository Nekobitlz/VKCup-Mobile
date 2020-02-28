package com.nekobitlz.unsubscribe.features.groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nekobitlz.unsubscribe.data.IGroupsRepository
import com.nekobitlz.unsubscribe.data.models.Group

class GroupsViewModel(
    private val groupsRepository: IGroupsRepository
) : ViewModel() {

    private var groupList: LiveData<List<Group>> = groupsRepository.getGroupList()

    private val selectionMode: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().apply { value = false }
    }

    private val selectionCount: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>().apply { value = 0 }
    }

    lateinit var unsubscribeStatus: LiveData<Pair<Int, String>>

    fun isSelectionMode(): LiveData<Boolean> = selectionMode
    fun getSelectionCount(): LiveData<Int> = selectionCount
    fun getGroupList(): LiveData<List<Group>> {
        return if (selectionMode.value!!) {
            groupList
        } else {
            val newList = groupsRepository.getGroupList() as MutableLiveData

            newList.value = groupList.value
            groupList = newList

            groupList
        }
    }

    fun onShortClicked(group: Group) {
        val count = selectionCount.value!!

        if (group.isChecked) {
            if (count == 0) {
                selectionMode.value = true
            }
            selectionCount.value = count + 1
        } else {
            selectionCount.value = count - 1
            if (count <= 1) {
                disableSelectionMode()
            }
        }
    }

    fun onUnsubscribeClicked() {
        val checkedGroup = groupList.value?.filter { it.isChecked }
        unsubscribeStatus = groupsRepository.unsubscribe(checkedGroup!!)
        disableSelectionMode()
    }

    private fun disableSelectionMode() {
        selectionCount.value = 0
        selectionMode.value = false
        groupList.value?.forEach { it.isChecked = false }
    }
}