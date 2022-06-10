package ru.nsu.vadim.model

data class Student(
    val name: String,
    var username: String = "",
    var repositoryUrl: String = "",
    var branch: String = "master",
    val grades: Grades = Grades(),
)