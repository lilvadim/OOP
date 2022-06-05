package ru.nsu.vadim.model

import ru.nsu.vadim.dsl.ConfigMarker

@ConfigMarker
data class Grades(private val grades: MutableList<Grade> = mutableListOf()) : MutableList<Grade> by grades