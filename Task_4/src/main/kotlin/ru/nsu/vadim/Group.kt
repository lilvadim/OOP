package ru.nsu.vadim

typealias Students = MutableList<Student>

class Group(val id: String, private val students: Students = mutableListOf()) : Students by students