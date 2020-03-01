package com.nekobitlz.products.data.requests

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VkRemoveFromFavouriteRequest(groupId: Int, productId: Int) : VKRequest<Int>("fave.removeProduct") {

    init {
        addParam("owner_id", -groupId)
        addParam("id", productId)
    }

    override fun parse(r: JSONObject): Int = r.getInt("response")
}