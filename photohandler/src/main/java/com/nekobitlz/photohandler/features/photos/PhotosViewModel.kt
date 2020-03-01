package com.nekobitlz.photohandler.features.photos

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nekobitlz.photohandler.data.models.Album
import com.nekobitlz.photohandler.data.models.Photo
import com.nekobitlz.photohandler.data.repository.IPhotosRepository

class PhotosViewModel(
    private val album: Album,
    private val repository: IPhotosRepository
) : ViewModel() {
    val photos: LiveData<List<Photo>> by lazy {
        repository.getPhotos(album.id)
    }

    lateinit var requestResult: LiveData<Boolean>

    fun addPhoto(photo: Uri) {
        requestResult = repository.addPhoto(album.id, photo)
    }
}
