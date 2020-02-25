package com.nekobitlz.unsubscribe.features.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GroupsViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor().newInstance()
    }
}