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

fun String.wrapWithHtml(lightTheme: Boolean): String {
    val background = if (!lightTheme) "background: rgb(18, 18, 18) !important;" else ""
    val color = if (!lightTheme) "color: rgb(210, 210, 210) !important;" else ""

    return """
            <html>
                <head>
                <meta charset="utf-8">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <style>
                    body {
                        $background
                        $color
                    }
                    figure.image {
                        display: table;
                        clear: both;
                        text-align: center;
                        margin: 0.9em auto;
                        min-width: 50px;
                    }
                    img {
                        max-width: 100%;
                        width: 100%;
                        height: auto;
                    }
                    .image, .image_resized {
                        max-width: 100%;
                        width: 100%;
                        height: auto;
                    }
                    
                    figure.media {
                        clear: both;
                        margin: 0.9em auto;
                        display: block;
                        min-width: 15em;
                    }
                    
                    figure.image figcaption {
                        display: table-caption;
                        caption-side: bottom;
                        word-break: break-word;
                        padding: .6em;
                        font-size: .75em;
                        outline-offset: -1px;
                    }
                </style>
                </head>
                <body>
                    $this
                </body>
            </html>
    """.trimIndent()
}