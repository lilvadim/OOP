package ru.nsu.vadim.dsl

import java.io.File
import javax.script.ScriptEngineManager

class App {

    lateinit var configuration: Configuration

    fun main(args: Array<String>) {
        val path = args[0]
        val scriptString: String = File(path).readText()
        parse(scriptString)
    }

    fun parse(ktsString: String): Configuration {
        try {
            val scriptEngine = ScriptEngineManager().getEngineByExtension("kts")
            val configuration = scriptEngine.eval(ktsString) as Configuration
            this.configuration = configuration
            configuration.print()
            return configuration
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    private fun Configuration.print() {
        println("> Group:")
        group.forEach { println(it) }
        println("> Tasks:")
        tasks.forEach { println(it) }
    }
}