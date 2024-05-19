package com.rasyidin.connectopia.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {
    const val DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

    fun currentDateTimeFormat(format: String = DEFAULT_DATE_TIME_FORMAT): String {
        return SimpleDateFormat(format, Locale.getDefault()).format(Date())
    }

    fun getLastChatTime(timestamp: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp

        val now = Calendar.getInstance()

        // Check if the chat was today
        if (calendar.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
            calendar.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {
            val timeFormat = SimpleDateFormat("HH.mm", Locale.getDefault())
            return timeFormat.format(timestamp)
        }

        // Check if the chat was yesterday
        val yesterday = Calendar.getInstance()
        yesterday.add(Calendar.DAY_OF_YEAR, -1)
        if (calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) &&
            calendar.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)) {
            return "Yesterday"
        }

        // Check if the chat was more than 2 days ago
        val twoDaysAgo = Calendar.getInstance()
        twoDaysAgo.add(Calendar.DAY_OF_YEAR, -2)
        if (timestamp < twoDaysAgo.timeInMillis) {
            val dateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
            return dateFormat.format(timestamp)
        }

        // Otherwise, the chat was more than a year ago
        val yearFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return yearFormat.format(timestamp)
    }
}