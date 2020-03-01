package com.nekobitlz.photohandler.features.photos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nekobitlz.photohandler.data.models.Album
import com.nekobitlz.photohandler.data.repository.IPhotosRepository
import com.nekobitlz.photohandler.data.repository.PhotosRepository

class PhotosViewModelFactory(
    private val album: Album,
    private val repository: IPhotosRepository = PhotosRepository()
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(Album::class.java, IPhotosRepository::class.java)
            .newInstance(album, repository)
    }
}