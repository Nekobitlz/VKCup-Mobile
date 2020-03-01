package com.nekobitlz.photohandler.data.requests

import com.nekobitlz.photohandler.data.models.Photo
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VkGetPhotosRequest(albumId: Int, photoSizes: Boolean = true) : VKRequest<List<Photo>>("photos.get") {
    init {
        addParam("album_id", albumId)
        addParam("photo_sizes", if (photoSizes) 1 else 0)
    }

    override fun parse(r: JSONObject): List<Photo> {
        val photos = r.getJSONObject("response").getJSONArray("items")
        val result = mutableListOf<Photo>()

        for (i in 0 until photos.length()) {
            val jsonObject = photos.getJSONObject(i)
            val sizes = jsonObject.getJSONArray("sizes")
            result.add(Photo.parse(jsonObject, sizes.getJSONObject(sizes.length() - 1)))
        }

        return result
    }
}