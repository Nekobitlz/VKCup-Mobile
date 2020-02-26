package com.nekobitlz.unsubscribe.features.groupinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nekobitlz.unsubscribe.data.IGroupsRepository
import com.nekobitlz.unsubscribe.data.models.Group

class GroupInfoViewModel(group: Group, groupRepository: IGroupsRepository) : ViewModel() {

    val friendsCount: LiveData<Int> = groupRepository.getFriendsCount(group)
    val lastPostDate: LiveData<Long> = groupRepository.getLastPostDate(group)
}