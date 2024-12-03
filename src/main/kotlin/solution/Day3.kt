package solution

import utils.readFileLines

object Day3 {

    private const val READ_FIRST_DIGIT = 4
    private const val CALC_RESULT = 5
    private const val ENABLE_INST = 8
    private const val DISABLE_INST = 12

    private val stateMachine = listOf(
        mapOf("m" to 1, "d" to 6),
        mapOf("u" to 2),
        mapOf("l" to 3),
        mapOf("(" to 4),
        mapOf("digit" to 4, "," to 5),
        mapOf("digit" to 5, ")" to 0),
        mapOf("o" to 7),
        mapOf("(" to 8, "n" to 9),
        mapOf(")" to 0),
        mapOf("'" to 10),
        mapOf("t" to 11),
        mapOf("(" to 12),
        mapOf(")" to 0)
    )

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        val ans = getCalculations(processInput())

        println("Part 1: $ans")
    }

    private fun part2() {
        val ans = getCalculations(processInput(), alwaysEnabled = false)

        println("Part 2: $ans")
    }

    private fun getCalculations(
        line: String,
        alwaysEnabled: Boolean = true,
    ): Long {
        var ans = 0L

        var state = 0

        var isEnabled = true

        var currText = ""
        var firstNum = 1

        line.forEach { c ->
            if (c.isDigit() && stateMachine[state].contains("digit")) {
                currText += c
            } else if (stateMachine[state].contains(c.toString())) {
                when (state) {
                    READ_FIRST_DIGIT -> {
                        firstNum = currText.toInt()
                        currText = ""
                    }
                    CALC_RESULT -> {
                        if (isEnabled || alwaysEnabled) {
                            val secondNum = currText.toInt()
                            ans += firstNum * secondNum
                        }
                        currText = ""
                    }
                    ENABLE_INST -> isEnabled = true
                    DISABLE_INST -> isEnabled = false
                }
                state = stateMachine[state][c.toString()]!!
            } else {
                state = stateMachine[0][c.toString()] ?: 0
                currText = ""
            }
        }

        return ans
    }

    private fun processInput(): String {
        return readFileLines("day3.txt").joinToString("")
    }
}