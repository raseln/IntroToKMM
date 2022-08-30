package com.rasel.introtokmm.utils

import kotlinx.datetime.LocalDateTime

fun LocalDateTime.toFormattedString(): String {
    return try {
        "$dayOfMonth ${month.name.substring(0, 3)}, $year ${hour}:${minute}"
    } catch (e: Exception) {
        ""
    }
}