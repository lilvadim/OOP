package ru.nsu.vadim.dsl

import ru.nsu.vadim.Configuration

fun config(init: Configuration.() -> Unit): Configuration {
    val configuration = Configuration()
    configuration.init()
    return configuration
}