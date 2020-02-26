package com.nekobitlz.unsubscribe.features.groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nekobitlz.unsubscribe.data.GroupsRepository
import com.nekobitlz.unsubscribe.data.IGroupsRepository
import com.nekobitlz.unsubscribe.data.models.Group

class GroupsViewModel(
    private val groupsRepository: IGroupsRepository
) : ViewModel() {

    private var groupList: LiveData<List<Group>> = groupsRepository.getGroupList()

    private val isLongTapMode: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().apply { value = false }
    }

    lateinit var unsubscribeStatus: LiveData<Pair<Int, String>>

    fun isLongTapMode(): LiveData<Boolean> = isLongTapMode
    fun getGroupList(): LiveData<List<Group>> {
        val newList = groupsRepository.getGroupList() as MutableLiveData
        newList.value = groupList.value
        groupList = newList
        return groupList
    }

    fun onLongClicked() {
        val isLongTap = isLongTapMode.value

        if (isLongTap!!) {
            groupList.value?.forEach { it.isChecked = false }
        }

        isLongTapMode.value = !isLongTapMode.value!!
    }

    fun onUnsubscribeClicked() {
        val checkedGroup = groupList.value?.filter { it.isChecked }
        unsubscribeStatus = groupsRepository.unsubscribe(checkedGroup!!)
        onLongClicked()
    }
}