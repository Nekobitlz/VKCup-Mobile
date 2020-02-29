package com.nekobitlz.products.utils

import android.app.Activity
import android.util.TypedValue

fun Activity.convertDpToPx(dp: Float): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dp,
    resources.displayMetrics
)

fun Activity.convertPxToDp(px: Float): Float = px / resources.displayMetrics.density