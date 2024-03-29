package ru.nsu.vadim.model

import ru.nsu.vadim.dsl.ConfigMarker

@ConfigMarker
data class Lessons(private val lessons: MutableSet<Lesson> = mutableSetOf()) : MutableSet<Lesson> by lessons