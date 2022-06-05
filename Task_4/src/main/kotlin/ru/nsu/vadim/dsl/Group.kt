package ru.nsu.vadim.dsl

typealias Students = MutableList<Student>

@ConfigMarker
class Group(val id: String, private val students: Students = mutableListOf()) : Students by students {
    fun student(name: String, init: Student.() -> Unit = {}) {
        this += Student(name).apply(init)
    }
}