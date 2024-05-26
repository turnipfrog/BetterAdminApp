package com.example.betteradminapp.data.tools

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale


object DateTools {
    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToDateViaInstant(dateToConvert: LocalDate): Date? {
        return Date.from(
            dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant()
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToLocalDateViaInstant(dateToConvert: Date): LocalDate? {
        return dateToConvert.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToDateFromLocalDateTime(dateToConvert: LocalDateTime): Date? {
        return Date.from(
            dateToConvert
                .atZone(ZoneId.systemDefault())
                .toInstant()
        )
    }

    fun formatDate(date: Date, pattern: String, locale: Locale): String {
        val dateFormat = SimpleDateFormat(pattern, locale)
        return dateFormat.format(date)
    }
}