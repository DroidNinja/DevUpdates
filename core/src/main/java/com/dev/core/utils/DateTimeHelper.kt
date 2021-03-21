package com.dev.core.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateTimeHelper {
    companion object {
        fun formatDate(format: String, unFormattedTime: String?): Long {
            val formattedTime: String
            try {
                val sdf = SimpleDateFormat(format)
                val date: Date = sdf.parse(unFormattedTime)
                return date.time
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return 0
        }
    }
}