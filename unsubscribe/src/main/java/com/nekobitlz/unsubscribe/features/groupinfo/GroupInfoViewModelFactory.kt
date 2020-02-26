package com.nekobitlz.unsubscribe.features.groupinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nekobitlz.unsubscribe.data.IGroupsRepository
import com.nekobitlz.unsubscribe.data.models.Group

class GroupInfoViewModelFactory(private val group: Group, private val repository: IGroupsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(Group::class.java, IGroupsRepository::class.java)
            .newInstance(group, repository)
    }
}