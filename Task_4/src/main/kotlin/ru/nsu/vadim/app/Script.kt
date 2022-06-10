package ru.nsu.vadim.app

import ru.nsu.vadim.dsl.Configuration
import javax.script.ScriptEngineManager

fun parse(ktsScript: String): Configuration {
    try {
        val scriptEngine = ScriptEngineManager().getEngineByExtension("kts")
        return scriptEngine.eval(ktsScript) as Configuration
    } catch (e: Exception) {
        e.printStackTrace()
        throw e
    }
}