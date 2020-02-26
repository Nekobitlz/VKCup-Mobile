package com.nekobitlz.unsubscribe.utils

import android.content.res.Resources
import android.text.format.DateFormat
import com.nekobitlz.unsubscribe.R
import java.util.*

fun Long.toHumanReadable(resources: Resources): String {
    val dateTime = Calendar.getInstance()
    dateTime.timeInMillis = this * 1000
    val currentTime = Calendar.getInstance()

    return when {
        this == (-1).toLong() -> resources.getString(R.string.date_not_yet_created)
        currentTime[Calendar.DATE] == dateTime[Calendar.DATE] -> resources.getString(R.string.date_today) + DateFormat.format(
            " HH:mm",
            dateTime
        )
        currentTime[Calendar.DATE] == dateTime[Calendar.DATE] - 1 -> resources.getString(R.string.date_yesterday)
        currentTime[Calendar.YEAR] == dateTime[Calendar.YEAR] -> DateFormat.format(
            "d MMM",
            dateTime
        ).toString()
        else -> DateFormat.format("d MMM yyyy", dateTime).toString()
    }

}