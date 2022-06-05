package ru.nsu.vadim.dsl

import java.time.LocalDate

@ConfigMarker
class Grades(private val grades: MutableList<Grade> = mutableListOf()) : MutableList<Grade> by grades {
    fun grade(name: String, date: LocalDate) {
        this += Grade(name, date)
    }
}