package com.example.commons.utility.helper

import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat

class DateUtils {

    companion object {
        fun currentDate(format: String): String {
            val current = LocalDate.now()
            val dtf = DateTimeFormat.forPattern(format)
            return dtf.print(current)
        }
    }

}