package ru.nsu.vadim.dsl

import java.time.LocalDate

@ConfigMarker
class Lessons(private val lessons: MutableSet<Lesson> = mutableSetOf()) : MutableSet<Lesson> by lessons {
    fun lesson(date: LocalDate) {
        this += Lesson(date)
    }
}