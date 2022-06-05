package ru.nsu.vadim.model

import ru.nsu.vadim.dsl.ConfigMarker

@ConfigMarker
data class Group(
    val id: String,
    private val students: MutableList<Student> = mutableListOf(),
) : MutableList<Student> by students