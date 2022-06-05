package ru.nsu.vadim

class Configuration(init: Configuration.() -> Unit) {
    lateinit var group: Group
    val tasks: Tasks = mutableListOf()

    init {
        this.init()
    }
}

