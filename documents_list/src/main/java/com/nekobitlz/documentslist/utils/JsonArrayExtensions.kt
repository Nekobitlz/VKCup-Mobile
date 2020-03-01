package com.nekobitlz.documentslist.utils

import org.json.JSONArray

fun JSONArray?.toStringList(): List<String>? {
    val result = mutableListOf<String>()

    if (this == null || this.length() == 0) {
        return null
    }

    for (i in 0 until length()) {
        val element = getString(i)
        result.add(element)
    }

    return result
}