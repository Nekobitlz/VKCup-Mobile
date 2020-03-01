package com.nekobitlz.products.data.requests

import com.nekobitlz.products.data.models.Product
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VkGetProductsRequest(groupId: Int, extended: Boolean = true) : VKRequest<List<Product>>("market.get") {
    init {
        addParam("owner_id", -groupId)
        addParam("extended", if (extended) 1 else 0)
    }

    override fun parse(r: JSONObject): List<Product> {
        val products = r.getJSONObject("response").getJSONArray("items")
        val result = mutableListOf<Product>()

        for (i in 0 until products.length()) {
            result.add(Product.parse(products.getJSONObject(i)))
        }

        return result
    }
}