package ru.nsu.vadim.dsl

data class Student(val name: String, var username: String = "", var repositoryUrl: String = "", var branch: String = "master")