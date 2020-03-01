package com.nekobitlz.unsubscribe.utils

import android.content.res.Resources
import android.text.format.DateFormat
import android.text.format.DateUtils
import com.nekobitlz.unsubscribe.R
import java.util.*

fun Long.toHumanReadable(resources: Resources): String {
    val dateTime = Calendar.getInstance()
    dateTime.timeInMillis = this * 1000
    val currentTime = Calendar.getInstance()

    return when {
        this == (-1).toLong() -> resources.getString(R.string.date_not_yet_created)
        DateUtils.isToday(this) -> resources.getString(R.string.date_today) + DateFormat.format(
            " HH:mm",
            dateTime
        )
        DateUtils.isToday(this - DateUtils.DAY_IN_MILLIS) -> resources.getString(R.string.date_yesterday)
        currentTime[Calendar.YEAR] == dateTime[Calendar.YEAR] -> DateFormat.format(
            "d MMM",
            dateTime
        ).toString()
        else -> DateFormat.format("d MMM yyyy", dateTime).toString()
    }
}