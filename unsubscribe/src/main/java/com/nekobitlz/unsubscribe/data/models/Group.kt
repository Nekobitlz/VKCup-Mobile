package com.nekobitlz.unsubscribe.data.models

import android.content.res.Resources
import com.nekobitlz.unsubscribe.R
import org.json.JSONObject
import java.io.Serializable

data class Group(
    val id: Int,
    val name: String,
    val screenName: String,
    val image: String,
    val subscribers: Int,
    val friends: Int = 0,
    val description: String,
    val lastPost: Int = 0,
    var isChecked: Boolean = false
) : Serializable {
    fun getFollowers(resources: Resources): String {
        return "$subscribers ${resources.getString(R.string.subscribers)} · $friends ${resources.getString(
            R.string.friends
        )}"
    }

    fun getLastPost(resources: Resources): String {
        return "${resources.getString(R.string.last_post)} $lastPost"
    }

    companion object CREATOR {

        fun parse(json: JSONObject) = Group(
            id = json.optInt("id", 0),
            name = json.optString("name", ""),
            screenName = json.optString("screen_name", ""),
            image = json.optString("photo_200", ""),
            subscribers = json.optInt("members_count", 0),
            description = json.optString("description", "")
        )
    }
}