package ru.nsu.vadim.dsl

import ru.nsu.vadim.Configuration
import ru.nsu.vadim.Group
import ru.nsu.vadim.Student
import ru.nsu.vadim.Students

class StudentsBuilder(private val students: Students = mutableListOf()) {
    fun student(name: String, config: Student.() -> Unit) {
        val student = Student(name)
        student.config()
        students += student
    }

    fun build() = students
}

fun Configuration.group(id: String, init: StudentsBuilder.() -> Unit) {
    val studentsBuilder = StudentsBuilder()
    studentsBuilder.init()
    group = Group(id, studentsBuilder.build())
}