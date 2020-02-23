package com.nekobitlz.documentslist.data.requests

import com.nekobitlz.documentslist.data.models.VKDocument
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VkGetDocumentsRequest(count: Int = 1, offset: Int = 0, ownerId: Int, returnTags: Int = 1) :
    VKRequest<List<VKDocument>>("docs.get") {

    init {
        //    addParam("count", count)
        addParam("offset", offset)
        addParam("owner_id", ownerId)
        addParam("return_tags", returnTags)
    }

    override fun parse(r: JSONObject): List<VKDocument> {
        val documents = r.getJSONObject("response").getJSONArray("items")

        val result = mutableListOf<VKDocument>()
        for (i in 0 until documents.length()) {
            result.add(VKDocument.parse(documents.getJSONObject(i)))
        }

        return result
    }
}