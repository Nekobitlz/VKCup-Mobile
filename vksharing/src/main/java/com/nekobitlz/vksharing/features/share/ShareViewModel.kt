package com.nekobitlz.vksharing.features.share

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nekobitlz.vksharing.data.IShareRepository

class ShareViewModel(
    private val repository: IShareRepository
) : ViewModel() {

    lateinit var requestResult: LiveData<Int>

    fun onSendClicked(userId: Int, uri: Uri?, text: String) {
       requestResult = repository.sendPost(userId = userId, message = text, attachment = uri)
    }
}