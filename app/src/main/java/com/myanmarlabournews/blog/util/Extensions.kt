package com.myanmarlabournews.blog.util

import android.content.Context
import android.content.ContextWrapper
import android.text.format.DateUtils
import androidx.activity.ComponentActivity
import java.text.NumberFormat
import java.util.Calendar
import java.util.Locale

fun Context.findActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

fun Long.compactFormat(): String {
    if (this < 1_000) {
        return "$this"
    }

    val format = NumberFormat.getInstance(Locale.US)
    format.maximumFractionDigits = 1

    if (this >= 1_000_000) {
        return "${format.format(this / 1_000_000.0)}M"
    }

    return "${format.format(this / 1000.0)}K"
}

fun Long.timeAgo(): String {
    return DateUtils.getRelativeTimeSpanString(
        this,
        Calendar.getInstance().timeInMillis,
        DateUtils.SECOND_IN_MILLIS
    ).toString()
}