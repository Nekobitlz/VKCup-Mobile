package com.nekobitlz.unsubscribe.features.groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nekobitlz.unsubscribe.data.GroupsRepository
import com.nekobitlz.unsubscribe.data.IGroupsRepository
import com.nekobitlz.unsubscribe.data.models.Group

class GroupsViewModel(
    private val groupsRepository: IGroupsRepository = GroupsRepository()
) : ViewModel() {

    private val groupList: MutableLiveData<List<Group>> by lazy {
        groupsRepository.getGroupList() as MutableLiveData<List<Group>>
    }

    private val isLongTapMode: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().apply { value = false }
    }

    fun isLongTapMode(): LiveData<Boolean> = isLongTapMode
    fun getGroupList(): LiveData<List<Group>> = groupList

    fun onLongClicked() {
        val isLongTap = isLongTapMode.value

        if (isLongTap!!) {
            groupList.value?.forEach { it.isChecked = false }
        }

        isLongTapMode.value = !isLongTapMode.value!!
    }
}