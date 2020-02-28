package com.nekobitlz.documentslist.utils

import android.content.res.Resources
import android.text.format.DateFormat
import android.text.format.DateUtils
import com.nekobitlz.documentslist.R
import java.util.*

fun Long.toHumanReadableSize(): String {
    var current = this.toFloat()
    var count = 0

    while (current > 1024 && count < 5) {
        current /= 1024
        count++
    }

    val resultBig = String.format("%.2f", current)
    val resultSmall = String.format("%.0f", current)

    return when (count) {
        0 -> "$resultSmall B"
        1 -> "$resultSmall Kb"
        2 -> "$resultBig Mb"
        3 -> "$resultBig Gb"
        4 -> "$resultBig Tb"
        else -> "$resultBig Pb"
    }
}

fun Long.toHumanReadableDate(resources: Resources): String {
    val dateTime = Calendar.getInstance()
    dateTime.timeInMillis = this * 1000
    val currentTime = Calendar.getInstance()

    return when {
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