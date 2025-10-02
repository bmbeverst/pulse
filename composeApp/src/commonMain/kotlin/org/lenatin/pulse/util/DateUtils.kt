package org.lenatin.pulse.util

import kotlinx.datetime.*

fun today(): LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

fun LocalDate.plus(days: Int): LocalDate = this.plus(DatePeriod(days = days))

fun LocalDate.minus(days: Int): LocalDate = this.minus(DatePeriod(days = days))

fun formatDate(date: LocalDate): String {
    // Simple, locale-agnostic short format (e.g., 2025-10-01 → "Oct 1, 2025")
    val monthNames = listOf("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec")
    val mon = monthNames[date.monthNumber - 1]
    return "$mon ${date.dayOfMonth}, ${date.year}"
}
