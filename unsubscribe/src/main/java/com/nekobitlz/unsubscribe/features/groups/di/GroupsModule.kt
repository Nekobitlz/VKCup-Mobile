package com.nekobitlz.unsubscribe.features.groups.di

import com.nekobitlz.unsubscribe.di.modules.ApplicationComponent
import com.nekobitlz.unsubscribe.features.groups.GroupsViewModelFactory

interface GroupsComponent {
    val groupsViewModelFactory: GroupsViewModelFactory
}

class GroupsModule(private val applicationComponent: ApplicationComponent) : GroupsComponent {
    override val groupsViewModelFactory: GroupsViewModelFactory
        get() = GroupsViewModelFactory(applicationComponent.groupsRepository)

}