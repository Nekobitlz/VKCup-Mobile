package com.nekobitlz.unsubscribe.features.groupinfo.di

import com.nekobitlz.unsubscribe.data.models.Group
import com.nekobitlz.unsubscribe.di.modules.ApplicationComponent
import com.nekobitlz.unsubscribe.features.groupinfo.GroupInfoViewModelFactory

interface GroupInfoComponent {
    val groupInfoViewModelFactory: GroupInfoViewModelFactory
}

class GroupInfoModule(private val group: Group, private val appComponent: ApplicationComponent) : GroupInfoComponent {
    override val groupInfoViewModelFactory: GroupInfoViewModelFactory
        get() = GroupInfoViewModelFactory(group, appComponent.groupsRepository)

}