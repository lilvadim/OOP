package ru.nsu.vadim.dsl

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Lessons(
    private val lessons: MutableList<Lesson> = mutableListOf(),
    val dateTimeFormatter: DateTimeFormatter
) : MutableList<Lesson> by lessons {

    fun lesson(date: LocalDate) {
        this += Lesson(date)
    }

    fun lesson(date: String) {
        this += Lesson(LocalDate.parse(date, dateTimeFormatter))
    }
}