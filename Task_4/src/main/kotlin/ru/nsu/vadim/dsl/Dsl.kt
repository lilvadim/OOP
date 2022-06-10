package ru.nsu.vadim.dsl

import ru.nsu.vadim.model.*
import java.time.LocalDate

fun config(init: Configuration.() -> Unit): Configuration = Configuration().apply(init)

fun Grades.grade(name: String, date: LocalDate): Grade {
    val grade = Grade(name, date)
    this += grade
    return grade
}

fun Grades.grade(name: String, date: String) =
    grade(name, LocalDate.parse(date, Configuration.dateTimeFormatter))


fun Group.student(name: String, init: Student.() -> Unit = {}) {
    this += Student(name).apply(init)
}

fun Student.grades(init: Grades.() -> Unit) = grades.init()

fun Lessons.lesson(date: LocalDate) {
    this += Lesson(date)
}

fun Lessons.lesson(date: String) = lesson(LocalDate.parse(date, Configuration.dateTimeFormatter))

fun Tasks.task(id: String, deadline: LocalDate, init: Task.() -> Unit = {}) {
    this += Task(id, deadline).apply(init)
}

fun Tasks.task(id: String, deadline: String, init: Task.() -> Unit = {}) =
    task(id, LocalDate.parse(deadline, Configuration.dateTimeFormatter), init)

fun Task.description(id: String, init: Task.Description.() -> Unit) {
    description = Task.Description(id = id).apply(init)
}