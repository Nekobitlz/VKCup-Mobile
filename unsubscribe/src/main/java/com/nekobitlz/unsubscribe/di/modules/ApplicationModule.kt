package com.nekobitlz.unsubscribe.di.modules

import com.nekobitlz.unsubscribe.data.GroupsRepository
import com.nekobitlz.unsubscribe.data.IGroupsRepository

interface ApplicationComponent {
    val groupsRepository: IGroupsRepository
}

class ApplicationModule : ApplicationComponent {
    override val groupsRepository: IGroupsRepository = GroupsRepository()
}