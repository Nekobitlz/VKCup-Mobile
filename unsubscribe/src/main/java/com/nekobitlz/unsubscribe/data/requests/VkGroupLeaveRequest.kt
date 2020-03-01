package com.nekobitlz.unsubscribe.data.requests

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VkGroupLeaveRequest(groupId: Int) : VKRequest<Int>("groups.leave") {
    init {
        addParam("group_id", groupId)
    }

    override fun parse(r: JSONObject): Int = r.getInt("response")
}