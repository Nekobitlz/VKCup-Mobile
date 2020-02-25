package com.nekobitlz.unsubscribe.data.requests

import com.nekobitlz.unsubscribe.data.models.Group
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VkGroupsGetRequest(userId: Int = 153528920, extended: Boolean = true) : VKRequest<List<Group>>("groups.get") {
    init {
       // addParam("user_id", userId)
        addParam("extended", if (extended) 1 else 0)
        addParam("fields", "description,members_count")
    }

    override fun parse(r: JSONObject): List<Group> {
        val groups = r.getJSONObject("response").getJSONArray("items")
        val result = mutableListOf<Group>()

        // downTo because we need less popularity groups in begin
        for (i in groups.length() - 1 downTo 0) {
            result.add(Group.parse(groups.getJSONObject(i)))
        }

        return result
    }
}