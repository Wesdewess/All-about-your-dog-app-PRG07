package com.wesdewess

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DateHandler {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d-M-yyyy", Locale.ENGLISH)

    fun compareToCurrentDate(){

    }

    fun findMostRecentDate(dates: ArrayList<HashMap<String, String>>?){
        val formattedDates: Array<LocalDate>? = null
        if (dates != null) {
            for ((key,element) in dates.withIndex()) {
                val date = LocalDate.parse(element["DATE"], formatter)
                formattedDates?.set(key, date)
            }
        }
    }
}