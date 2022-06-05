package ru.nsu.vadim.model

import ru.nsu.vadim.dsl.ConfigMarker
import java.time.LocalDate

@ConfigMarker
data class Task(var id: String, var deadline: LocalDate, var description: Description = Description()) {
    data class Description(var id: String = "", var text: String = "", var score: Int = 1)
}