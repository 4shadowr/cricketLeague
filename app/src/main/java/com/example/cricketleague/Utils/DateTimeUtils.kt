package com.example.cricketleague.Utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DateTimeUtils {
    const val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
    private val formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).withLocale(Locale.US)
    @JvmStatic
    fun getDateTimeFromString(dateTime: String?): LocalDateTime {
        return LocalDateTime.parse(dateTime, formatter)
    }
}