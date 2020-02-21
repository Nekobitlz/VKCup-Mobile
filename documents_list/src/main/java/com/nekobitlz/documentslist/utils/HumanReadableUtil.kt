package com.nekobitlz.documentslist.utils

import android.text.format.DateFormat
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

fun Long.toHumanReadableDate(): String {
    val dateTime = Calendar.getInstance()
    dateTime.timeInMillis = this * 1000
    val currentTime = Calendar.getInstance()

    return when {
        currentTime[Calendar.DATE] == dateTime[Calendar.DATE] -> "today" + DateFormat.format(
            " HH:mm",
            dateTime
        )
        currentTime[Calendar.DATE] == dateTime[Calendar.DATE] - 1 -> "yesterday"
        currentTime[Calendar.YEAR] == dateTime[Calendar.YEAR] -> DateFormat.format(
            "d MMM",
            dateTime
        ).toString()
        else -> DateFormat.format("d MMM yyyy", dateTime).toString()
    }

}