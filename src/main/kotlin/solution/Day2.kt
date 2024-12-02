package solution

import utils.readFileLines

object Day2 {

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        var ans = 0

        processInput { report ->
            if (checkReportIsSafe(report)) ans++
        }

        println("Part 1: $ans")
    }

    private fun part2() {
        var ans = 0

        processInput { report ->
            val isSafe = checkReportIsSafe(report) || report.indices.any { i ->
                checkReportIsSafe(report, skip = i)
            }

            if (isSafe) ans++
        }


        println("Part 2: $ans")
    }

    private fun checkReportIsSafe(
        report: List<Int>,
        skip: Int = -1,
    ): Boolean {
        val isIncreasing = when (skip) {
            0 -> report[2] - report[1] > 0
            1 -> report[2] - report[0] > 0
            else -> report[1] - report[0] > 0
        }

        var isValid = true
        var prev = if (skip == 0) report[1] else report[0]

        val start = if (skip == 0) 2 else 1

        for (i in start until report.size) {
            if (skip == i) continue

            val diff = if (isIncreasing) report[i] - prev else prev - report[i]
            if (diff !in 1..3) {
                isValid = false
                break
            }

            prev = report[i]
        }

        return isValid
    }

    private fun processInput(action: (a: List<Int>) -> Unit) {
        return readFileLines("day2.txt") {
            action(it.split(" ").map { it.toInt() })
        }
    }

}