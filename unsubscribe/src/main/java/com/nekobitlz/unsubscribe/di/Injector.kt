package com.nekobitlz.unsubscribe.di

import com.nekobitlz.unsubscribe.UnsubscribeApp
import com.nekobitlz.unsubscribe.data.models.Group
import com.nekobitlz.unsubscribe.di.modules.ApplicationComponent
import com.nekobitlz.unsubscribe.di.modules.ApplicationModule
import com.nekobitlz.unsubscribe.features.groupinfo.di.GroupInfoComponent
import com.nekobitlz.unsubscribe.features.groupinfo.di.GroupInfoModule
import com.nekobitlz.unsubscribe.features.groups.di.GroupsComponent
import com.nekobitlz.unsubscribe.features.groups.di.GroupsModule

class Injector {

    val applicationComponent: ApplicationComponent = ApplicationModule()
    val groupsComponent: GroupsComponent = GroupsModule(applicationComponent)

    fun getGroupInfoModule(group: Group): GroupInfoComponent = GroupInfoModule(group, applicationComponent)
}

val injector: Injector get() = UnsubscribeApp.injector