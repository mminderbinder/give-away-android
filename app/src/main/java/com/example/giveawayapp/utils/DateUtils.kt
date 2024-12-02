package com.example.giveawayapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils
{
    fun formatDate(date: Date?, format: String = "MMM dd, yyyy"): String
    {
        return if (date != null)
        {
            val dateFormat = SimpleDateFormat(format, Locale.getDefault())
            dateFormat.format(date)
        }
        else
        {
            "Date not available"
        }
    }
}