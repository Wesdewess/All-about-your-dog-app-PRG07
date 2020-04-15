package com.wesdewess

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList

class DateHandler {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d-M-yyyy", Locale.ENGLISH)

    private fun formatDateFromString(date: String?): LocalDate{
        return LocalDate.parse(date, formatter)
    }

    fun compareToCurrentDate(date: String?): Long{
        val fDate = formatDateFromString(date)
        val currentDate = LocalDate.now()
        return fDate.until(currentDate, ChronoUnit.DAYS)
    }

    fun compareToCurrentDate(dates: ArrayList<HashMap<String, String>>?): Long? {
        val currentDate = LocalDate.now()
        println("Current date: $currentDate")
        return findMostRecentDate(dates)?.until(currentDate, ChronoUnit.DAYS)
    }

    private fun findMostRecentDate(dates: ArrayList<HashMap<String, String>>?): LocalDate? {
        val formattedDates = ArrayList<LocalDate>()

        if (dates!!.isNotEmpty()) {
            for (element in dates) {
                val date = LocalDate.parse(element["DATE"], formatter)
                formattedDates.add(date)
            }
        }else{
            println("No values in dates array")
            return LocalDate.now()
        }

        var recentDate = formattedDates[0]
        for (fDate in formattedDates)

            if (recentDate < fDate) {
            recentDate = fDate
        }
        println("Most recent date: $recentDate")
        return recentDate
    }
}