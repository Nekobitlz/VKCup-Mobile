package com.nekobitlz.photohandler.features.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nekobitlz.photohandler.data.repository.AlbumsRepository
import com.nekobitlz.photohandler.data.repository.IAlbumsRepository

class AlbumsViewModelFactory(private val repository: IAlbumsRepository = AlbumsRepository()) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(IAlbumsRepository::class.java)
            .newInstance(repository)
    }
}