package com.nekobitlz.documentslist.data.models

import android.content.res.Resources
import com.nekobitlz.documentslist.utils.toStringList
import com.nekobitlz.documentslist.utils.toHumanReadableDate
import com.nekobitlz.documentslist.utils.toHumanReadableSize
import org.json.JSONObject
import java.io.Serializable

data class VKDocument(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val size: Long,
    val ext: String,
    val url: String,
    val date: Long,
    val type: Int,
    val tags: String
) : Serializable {
    fun getParameters(resources: Resources): String {
        return "${ext.toUpperCase()} · ${size.toHumanReadableSize()} · ${date.toHumanReadableDate(resources)}".trim()
    }

    fun getType(): VkDocumentType =
        VkDocumentType.createVkDocumentType(type)

    companion object CREATOR {

        fun parse(json: JSONObject) = VKDocument(
            id = json.optInt("id", 0),
            ownerId = json.optInt("owner_id", 0),
            title = json.optString("title", ""),
            size = json.optLong("size", 0),
            ext = json.optString("ext", ""),
            url = json.optString("url", ""),
            date = json.optLong("date", 0),
            type = json.optInt("type", 0),
            tags = json.optJSONArray("tags")
                ?.toStringList()
                ?.joinToString(", ")
                ?: ""
        )
    }
}