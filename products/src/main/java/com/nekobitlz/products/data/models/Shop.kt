package com.nekobitlz.products.data.models

import android.content.res.Resources
import com.nekobitlz.products.R
import org.json.JSONObject
import java.io.Serializable

data class Shop(
    val id: Int,
    val name: String,
    val avatar: String,
    val status: Int
) : Serializable {

    fun getStatus(resources: Resources): String = when (status) {
        0 -> resources.getString(R.string.shop_status_open)
        1 -> resources.getString(R.string.shop_status_closed)
        else -> resources.getString(R.string.shop_status_private)
    }

    companion object CREATOR {

        fun parse(json: JSONObject) = Shop(
            id = json.optInt("id", 0),
            name = json.optString("name", ""),
            avatar = json.optString("photo_200", ""),
            status = json.optInt("is_closed", 0)
        )
    }
}