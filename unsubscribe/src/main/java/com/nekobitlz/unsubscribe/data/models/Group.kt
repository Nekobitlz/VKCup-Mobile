package com.nekobitlz.unsubscribe.data.models

import android.content.res.Resources
import com.nekobitlz.unsubscribe.R
import com.nekobitlz.unsubscribe.utils.toHumanReadable
import com.nekobitlz.unsubscribe.utils.toShortType
import org.json.JSONObject
import java.io.Serializable

data class Group(
    val id: Int,
    val name: String,
    val screenName: String,
    val image: String,
    val subscribers: Int,
    var friends: Int = 0,
    val description: String,
    var lastPost: Long = 0,
    var isChecked: Boolean = false
) : Serializable {
    fun getFollowers(resources: Resources): String {
        val subscribersText = "${subscribers.toShortType()} ${resources.getString(R.string.group_info_subscribers)}"
        val friendsText = if (friends > 0) {
            " · $friends ${resources.getString(R.string.group_info_friends)}"
        } else {
            ""
        }

        return subscribersText + friendsText
    }

    fun getLastPost(resources: Resources): String {
        return "${resources.getString(R.string.group_info_last_post)} ${lastPost.toHumanReadable(resources)}"
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