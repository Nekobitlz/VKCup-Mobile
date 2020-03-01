package com.nekobitlz.products.data.models

import org.json.JSONObject

data class City(
    val id: Int,
    val name: String,
    var isChecked: Boolean = false
) {

    companion object CREATOR {

        fun parse(json: JSONObject) = City(
            id = json.optInt("id", 0),
            name = json.optString("title", "")
        )
    }
}