package com.nekobitlz.unsubscribe.features.groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nekobitlz.unsubscribe.data.GroupsRepository
import com.nekobitlz.unsubscribe.data.IGroupsRepository
import com.nekobitlz.unsubscribe.data.models.Group

class GroupsViewModel(
    private val groupsRepository: IGroupsRepository = GroupsRepository()
): ViewModel() {

    val groupList: LiveData<List<Group>> = groupsRepository.getGroupList()
    private val isLongTapMode: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().apply { value = false }
    }

    fun isLongTapMode(): LiveData<Boolean> = isLongTapMode

    fun onLongClicked() {
        isLongTapMode.value = !isLongTapMode.value!!
    }

    fun onShortClicked(group: Group) {

    }
}