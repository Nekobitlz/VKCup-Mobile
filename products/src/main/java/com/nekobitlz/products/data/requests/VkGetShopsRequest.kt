package com.nekobitlz.products.data.requests

import com.nekobitlz.products.data.models.Shop
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VkGetShopsRequest(q: String = " ", cityId: Int = 0, market: Boolean = true) :
    VKRequest<List<Shop>>("groups.search") {

    init {
        addParam("q", q)
        addParam("city_id", cityId)
        addParam("market", if (market) 1 else 0)
    }

    override fun parse(r: JSONObject): List<Shop> {
        val shops = r.getJSONObject("response").getJSONArray("items")

        val result = mutableListOf<Shop>()
        for (i in 0 until shops.length()) {
            result.add(Shop.parse(shops.getJSONObject(i)))
        }

        return result
    }
}