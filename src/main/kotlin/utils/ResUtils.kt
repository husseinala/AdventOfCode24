package utils

import java.io.File

private object ResUtils

fun readFile(name: String): File {
    return File(ResUtils::class.java.getResource("../$name")!!.file)
}

fun readFileLines(name: String, action: (line: String) -> Unit) {
    readFile(name).forEachLine(action = action)
}

fun readFileLines(name: String): List<String> {
    return buildList { readFileLines(name) { add(it) } }
}

fun readFileAsString(name: String, separator: CharSequence = ", "): String {
    return buildString {
        readFileLines(name) { line ->
            if (isNotEmpty()) append(separator)
            append(line)
        }
    }
}