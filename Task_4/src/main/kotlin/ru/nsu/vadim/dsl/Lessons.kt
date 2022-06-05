package ru.nsu.vadim.dsl

import java.time.LocalDate

@ConfigMarker
class Lessons(private val lessons: MutableList<Lesson> = mutableListOf()) : MutableList<Lesson> by lessons {
    fun lesson(date: LocalDate) {
        this += Lesson(date)
    }
}