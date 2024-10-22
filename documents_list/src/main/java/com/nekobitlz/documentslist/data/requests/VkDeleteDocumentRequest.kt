package com.nekobitlz.documentslist.data.requests

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VkDeleteDocumentRequest(ownerId: Int, docId: Int) : VKRequest<Int>("docs.delete") {

    init {
        addParam("owner_id", ownerId)
        addParam("doc_id", docId)
    }

    override fun parse(r: JSONObject): Int = r.getInt("response")
}