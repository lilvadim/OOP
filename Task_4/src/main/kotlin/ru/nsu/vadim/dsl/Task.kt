package ru.nsu.vadim.dsl

import java.time.LocalDate

@ConfigMarker
data class Task(var id: String, var deadline: LocalDate, var description: Description = Description()) {

    fun toConventionName(): String = "Task_${convertId(id)}"

    data class Description(var id: String = "", var text: String = "", var score: Int = 1)

    fun description(id: String, init: Description.() -> Unit) {
        description = Description(id = id).apply(init)
    }
}

private fun convertId(id: String) = id.replace(".", "_")

