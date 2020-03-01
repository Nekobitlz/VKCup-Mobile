package com.nekobitlz.photohandler.features.albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nekobitlz.photohandler.data.models.Album
import com.nekobitlz.photohandler.data.repository.IAlbumsRepository

class AlbumsViewModel(private val repository: IAlbumsRepository) : ViewModel() {
    private var albums: LiveData<List<Album>>
    private val isEditableMode: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    init {
        albums = repository.getAlbums()
    }

    lateinit var requestResult: LiveData<Boolean>

    fun getAlbums(): LiveData<List<Album>> {
        val newAlbums = repository.getAlbums() as MutableLiveData
        newAlbums.value = albums.value
        albums = newAlbums
        return albums
    }

    fun isEditableMode(): LiveData<Boolean> = isEditableMode

    fun onAddClicked() {
        requestResult = repository.createAlbum()
    }

    fun onEditClicked() {
        isEditableMode.value = true
    }

    fun onCloseClicked() {
        isEditableMode.value = false
    }

    fun onRemoveClicked(album: Album) {
        requestResult = repository.removeAlbum(album.id)
    }
}