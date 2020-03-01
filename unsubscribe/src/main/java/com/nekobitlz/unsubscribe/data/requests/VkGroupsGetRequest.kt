package com.nekobitlz.unsubscribe.data.requests

import com.nekobitlz.unsubscribe.data.models.Group
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VkGroupsGetRequest(extended: Boolean = true) : VKRequest<List<Group>>("groups.get") {
    init {
        addParam("extended", if (extended) 1 else 0)
        addParam("fields", "description,members_count,screen_name")
    }

    override fun parse(r: JSONObject): List<Group> {
        val groups = r.getJSONObject("response").getJSONArray("items")
        val result = mutableListOf<Group>()

        // downTo because we need the least popular groups in beginning
        for (i in groups.length() - 1 downTo 0) {
            result.add(Group.parse(groups.getJSONObject(i)))
        }

        return result
    }
}