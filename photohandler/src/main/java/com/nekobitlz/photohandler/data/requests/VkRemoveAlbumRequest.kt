package com.nekobitlz.photohandler.data.requests

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VkRemoveAlbumRequest(albumId: Int) : VKRequest<Int>("photos.deleteAlbum") {
    init {
        addParam("album_id", albumId)
    }

    override fun parse(r: JSONObject): Int = r.getInt("response")
}