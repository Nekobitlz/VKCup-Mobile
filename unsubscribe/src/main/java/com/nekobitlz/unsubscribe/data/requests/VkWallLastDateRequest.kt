package com.nekobitlz.unsubscribe.data.requests

import android.util.Log
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VkWallLastDateRequest(ownerId: Int, count: Int = 1, extended: Boolean = false) :
    VKRequest<Long>("wall.get") {

    init {
        Log.e("VK", "owner id $ownerId")
        addParam("owner_id", -ownerId)
        addParam("count", count)
        addParam("extended", if (extended) 1 else 0)
    }

    override fun parse(r: JSONObject): Long {
        val response = r.getJSONObject("response")
        return if (response.getInt("count") != 0) {
            response.getJSONArray("items")
                .getJSONObject(0)
                .getLong("date")
        } else {
            -1
        }
    }
}