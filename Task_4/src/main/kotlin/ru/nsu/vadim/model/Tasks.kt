package ru.nsu.vadim.model

import ru.nsu.vadim.dsl.ConfigMarker

@ConfigMarker
class Tasks(private val tasks: MutableList<Task> = mutableListOf()) : MutableList<Task> by tasks