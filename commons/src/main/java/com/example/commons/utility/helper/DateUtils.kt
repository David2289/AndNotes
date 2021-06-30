package com.example.commons.utility.helper

import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

class DateUtils {

    companion object {

        fun currentDate(format: String): String {
            val current = LocalDate.now()
            val dtf = DateTimeFormat.forPattern(format)
            return dtf.print(current)
        }

        fun date(inputMillis: Long, outputFormat: String): String {
            val inputDateTime =  DateTime(inputMillis)

            val dtfOut: DateTimeFormatter = DateTimeFormat.forPattern(outputFormat)
            return dtfOut.print(inputDateTime)
        }

    }

}