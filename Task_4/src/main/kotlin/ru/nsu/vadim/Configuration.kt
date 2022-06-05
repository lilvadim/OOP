package ru.nsu.vadim

typealias Tasks = MutableList<Task>

class Configuration {
    lateinit var group: Group
    val tasks: Tasks = mutableListOf()
}

