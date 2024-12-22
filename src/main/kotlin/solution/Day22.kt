package solution

import utils.*
import kotlin.math.max

object Day22 {

    private const val PRUNE_NUM = 16777216L

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        val ans = processInput().sumOf {
            var num = it
            repeat(2000) { num = nextNumber(num) }
            num
        }

        println("Part 1: $ans")
    }

    private fun part2() {
        val input = processInput()

        val memo = mutableMapOf<String, Long>()
        var max = 0L

        input.forEach {
            val seen = mutableSetOf<String>()
            val window = ArrayDeque<Long>()
            var n = it

            repeat(2000) {
                val a = n % 10
                n = nextNumber(n)
                val b = n % 10

                window.addLast(b - a)

                if (window.size > 4) window.removeFirst()

                val key = window.joinToString()
                if (window.size == 4 && !seen.contains(key)) {
                    seen.add(key)
                    memo[key] = (memo[key] ?: 0) + b
                    max = max(max, memo[key]!!)
                }
            }
        }

        println("Part 2: $max")
    }

    private fun nextNumber(n: Long): Long {
        return n.let { (it xor (it * 64)) % PRUNE_NUM }
            .let { (it xor (it / 32)) % PRUNE_NUM }
            .let { (it xor (it * 2048)) % PRUNE_NUM }
    }

    private fun processInput(): List<Long> {
        return readFileLines("day22.txt").map { it.toLong() }
    }
}