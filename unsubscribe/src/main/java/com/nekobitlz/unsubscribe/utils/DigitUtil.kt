package com.nekobitlz.unsubscribe.utils

fun Int.toShortType(): String = when {
    this < 1000 -> this.toString()
    this < 1000 * 1000 -> String.format("%.1f", this.toDouble() / 1000) + "K"
    else -> String.format("%.1f", this.toDouble() / (1000 * 1000)) + "M"
}