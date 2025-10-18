package org.lenatin.pulse.util

import kotlinx.datetime.*
import kotlinx.datetime.format.*

fun today(): LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

fun LocalDate.plus(days: Int): LocalDate = this.plus(DatePeriod(days = days))

fun LocalDate.minus(days: Int): LocalDate = this.minus(DatePeriod(days = days))

fun formatDate(date: LocalDate): String {
    // Simple, locale-agnostic short format (e.g., 2025-10-01 → "Oct 1, 2025")
    val formatter = LocalDate.Format {
        monthName(MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        dayOfMonth()
        chars(", ")
        year()
    }
    return date.format(formatter) // 'Tue, 3 Jun 2008 11:05:30 GMT'
}
