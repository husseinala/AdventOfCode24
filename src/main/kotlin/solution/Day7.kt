package solution

import utils.readFileLines

private typealias Operation = (a: Long, b: Long) -> Long

private val Plus: Operation = { a, b -> a + b }
private val Times: Operation = { a, b -> a * b }
private val Concatenate: Operation = { a, b -> "$a$b".toLong() }

object Day7 {

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        var ans = 0L

        val operations = listOf(Plus, Times)

        processInput { req, values ->
            if (backtrack(start = 1, values, operations, values[0].toLong(), req)) {
                ans += req
            }
        }

        println("Part 1: $ans")
    }

    private fun part2() {
        var ans = 0L

        val operations = listOf(Plus, Times, Concatenate)

        processInput { req, values ->
            if (backtrack(start = 1, values, operations, values[0].toLong(), req)) {
                ans += req
            }
        }

        println("Part 2: $ans")
    }

    private fun backtrack(start: Int, values: List<Int>, operations: List<Operation>, curr: Long, req: Long): Boolean {
        if (curr > req) return false
        if (start == values.size) return curr == req

        return operations.any { op ->
            backtrack(start + 1, values, operations, op(curr, values[start].toLong()), req)
        }
    }

    private fun processInput(action: (Long, List<Int>) -> Unit) {
        readFileLines("day7.txt") { line ->
            val (total, values) = line.split(": ")

            action(total.toLong(), values.split(" ").map { it.toInt() })
        }
    }
}
