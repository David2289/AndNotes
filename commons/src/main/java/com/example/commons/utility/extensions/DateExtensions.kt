package com.example.commons.utility.extensions

import org.joda.time.*
import org.joda.time.format.DateTimeFormat



/**
 * Input example:
 *      DateTime.now() to get current date time in default zone (current zone)
 *      DateTime.now(DateTimeZone.UTC) to get current date time in UTC
 *      DateTime.now(DateTimeZone.forID("Europe/London")) to get current date time in specific zone
 * outFormat: "dd/MM/yyyy HH:mm"
 */
fun DateTime.display(outFormat: String): String {
    val dtf = DateTimeFormat.forPattern(outFormat)
    return dtf.print(this)
}

/**
 * Input example: LocalDateTime.now()
 * outFormat: "dd/MM/yyyy HH:mm"
 */
fun LocalDateTime.display(outFormat: String): String {
    val dtf = DateTimeFormat.forPattern(outFormat)
    return dtf.print(this)
}

/**
 * Input example: LocalDate.now()
 * outFormat: "dd/MM/yyyy HH:mm"
 */
fun LocalDate.display(outFormat: String): String {
    val dtf = DateTimeFormat.forPattern(outFormat)
    return dtf.print(this)
}

/**
 * Input example: LocalTime.now()
 * outFormat: "dd/MM/yyyy HH:mm"
 */
fun LocalTime.display(outFormat: String): String {
    val dtf = DateTimeFormat.forPattern(outFormat)
    return dtf.print(this)
}


/**
 * Input example: UTC "2021-09-05T16:36:06Z"
 * outFormat: "dd/MM/yyyy HH:mm"
 */
fun String.utcToLocalDate(outFormat: String): String {
    val dt = DateTime(this, DateTimeZone.UTC).withZone(DateTimeZone.getDefault())
    val dtf = DateTimeFormat.forPattern(outFormat)
    return dtf.print(dt)
}

/**
 * Input example: 1630863586846
 * outFormat: "dd/MM/yyyy HH:mm"
 */
fun Long.toDate(outFormat: String): String {
    val dt = LocalDateTime(this)
    val dtf = DateTimeFormat.forPattern(outFormat)
    return dtf.print(dt)
}

/**
 * Input example: "Europe/London"
 * outFormat: "dd/MM/yyyy HH:mm"
 */
fun String.toDateTimeZone(): DateTimeZone {
    return DateTimeZone.forID(this)
}

/**
 * from: 1630863286846
 * to: current time
 */
fun Duration.howLong(from: Long, to: Long = Instant().millis) {

}