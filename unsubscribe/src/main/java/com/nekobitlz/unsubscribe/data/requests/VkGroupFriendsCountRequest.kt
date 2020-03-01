package com.nekobitlz.unsubscribe.data.requests

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VkGroupFriendsCountRequest(groupId: Int, filter: String = "friends") : VKRequest<Int>("groups.getMembers") {
    init {
        addParam("group_id", groupId)
        addParam("filter", filter)
    }

    override fun parse(r: JSONObject): Int = r.getJSONObject("response").getInt("count")
}