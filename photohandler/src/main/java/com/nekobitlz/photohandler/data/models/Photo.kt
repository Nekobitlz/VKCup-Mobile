package com.nekobitlz.photohandler.data.models

import org.json.JSONObject

data class Photo(
    val id: Int,
    val url: String
) {

    companion object CREATOR {
        fun parse(r: JSONObject, photo: JSONObject): Photo = Photo(
            id = r.optInt("id", 0),
            url = photo.optString("url", "")
            )
    }
}