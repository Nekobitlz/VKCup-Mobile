package com.nekobitlz.photohandler.data.requests

import com.nekobitlz.photohandler.data.models.Album
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VkGetAlbumsRequest(
    needCovers: Boolean = true, needSystem: Boolean = true, photoSizes: Boolean = true
) : VKRequest<List<Album>>("photos.getAlbums") {
    init {
        addParam("need_system", if (needSystem) 1 else 0)
        addParam("need_covers", if (needCovers) 1 else 0)
        addParam("photo_sizes", if (photoSizes) 1 else 0)
    }

    override fun parse(r: JSONObject): List<Album> {
        val albums = r.getJSONObject("response").getJSONArray("items")
        val result = mutableListOf<Album>()

        for (i in 0 until albums.length()) {
            val jsonObject = albums.getJSONObject(i)
            val sizes = jsonObject.getJSONArray("sizes")
            result.add(Album.parse(jsonObject, sizes.getJSONObject(sizes.length() - 1)))
        }

        return result
    }
}