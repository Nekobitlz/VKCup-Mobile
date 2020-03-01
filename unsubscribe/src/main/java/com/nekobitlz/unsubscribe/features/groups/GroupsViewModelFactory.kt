package com.nekobitlz.unsubscribe.features.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nekobitlz.unsubscribe.data.IGroupsRepository

class GroupsViewModelFactory(private val groupsRepository: IGroupsRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(IGroupsRepository::class.java)
            .newInstance(groupsRepository)
    }
}