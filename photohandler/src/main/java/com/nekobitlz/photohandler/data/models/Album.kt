package com.nekobitlz.photohandler.data.models

import android.content.res.Resources
import com.nekobitlz.photohandler.R
import org.json.JSONObject
import java.io.Serializable

data class Album(
    val id: Int,
    val name: String,
    val photo: String,
    val photoCount: Int
) : Serializable {
    fun getPhotoCount(resources: Resources): String =
        "$photoCount ${resources.getString(R.string.album_photo_count)}"

    companion object CREATOR {
        fun parse(r: JSONObject, sizes: JSONObject): Album = Album(
            id = r.optInt("id", 0),
            name = r.optString("title", ""),
            photo = sizes.optString("src", ""),
            photoCount = r.optInt("size", 0)
        )
    }
}