package com.nekobitlz.products.data.models

import org.json.JSONObject

data class Product(
    val id: Int,
    val name: String,
    val price: String,
    val description: String,
    val avatar: String,
    val isFavourite: Boolean = false
) {
  //  fun getPrice(): String = price.toString().formatMoney()

    companion object CREATOR {

        fun parse(json: JSONObject) = Product(
            id = json.optInt("id", 0),
            name = json.optString("title", ""),
            price = json.getJSONObject("price").optString("text", ""),
            description = json.optString("description", ""),
            avatar = json.optString("thumb_photo")
        )
    }
}