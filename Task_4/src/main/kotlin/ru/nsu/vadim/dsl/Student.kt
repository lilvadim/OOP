package ru.nsu.vadim.dsl

data class Student(
    val name: String,
    var username: String = "",
    var repositoryUrl: String = "",
    var branch: String = "master",
    val grades: Grades = Grades(),
    val lessons: Lessons = Lessons(),
) {
    fun grades(init: Grades.() -> Unit) = grades.init()
    fun lessons(init: Lessons.() -> Unit) = lessons.init()
}