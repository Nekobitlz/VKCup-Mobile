package com.nekobitlz.photohandler.data.requests

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VkAddAlbumRequest : VKRequest<Int>("photos.createAlbum") {
    init {
        addParam("title", "Empty album")
    }

    override fun parse(r: JSONObject): Int = r.getJSONObject("response").getInt("id")
}