package com.nekobitlz.photohandler.features.albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nekobitlz.photohandler.data.models.Album
import com.nekobitlz.photohandler.data.repository.IAlbumsRepository

class AlbumsViewModel(private val repository: IAlbumsRepository) : ViewModel() {

    val albums: LiveData<List<Album>> by lazy {
        repository.getAlbums()
    }
}