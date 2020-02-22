package com.nekobitlz.documentslist.data.requests

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VkRenameDocumentRequest(ownerId: Int, docId: Int, title: String, returnTags: Int = 0) :
    VKRequest<Int>("docs.edit") {

    init {
        addParam("owner_id", ownerId)
        addParam("doc_id", docId)
        addParam("title", title)
        addParam("return_tags", returnTags)
    }

    override fun parse(r: JSONObject): Int = r.getInt("response")
}