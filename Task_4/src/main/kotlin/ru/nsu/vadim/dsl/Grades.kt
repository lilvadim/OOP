package ru.nsu.vadim.dsl

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Grades(
    private val grades: MutableList<Grade> = mutableListOf(),
    val dateTimeFormatter: DateTimeFormatter
) : MutableList<Grade> by grades {
    fun grade(name: String, date: LocalDate) {
        this += Grade(name, date)
    }

    fun grade(name: String, date: String) = grade(name, LocalDate.parse(date, dateTimeFormatter))
}