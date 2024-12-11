package solution

import utils.readFileLines
import kotlin.math.abs
import kotlin.math.log10

object Day11 {

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        val ans = findNumberOfStonesAfter(blinks = 25)

        println("Part 1: $ans")
    }

    private fun part2() {
        val ans = findNumberOfStonesAfter(blinks = 75)

        println("Part 2: $ans")
    }

    private fun findNumberOfStonesAfter(blinks: Int): Long {
        val memo = mutableMapOf<Pair<Long, Int>, Long>()

        return processInput().sumOf { initialStone ->
            search(initialStone, blinks, 0, memo)
        }
    }

    private fun search(stone: Long, target: Int, count: Int, memo: MutableMap<Pair<Long, Int>, Long>): Long {
        if (count == target) return 1L
        if (memo.contains(stone to count)) return memo[stone to count]!!

        val ans = if (stone == 0L) {
            search(stone = 1L, target, count + 1, memo)
        } else if (stone.length() % 2 == 0) {
            val (a, b) = stone.toString().chunked(stone.length() / 2)
            search(stone = a.toLong(), target, count + 1, memo) +
                    search(stone = b.toLong(), target, count + 1, memo)
        } else {
            search(stone = stone * 2024, target, count + 1, memo)
        }

        memo[stone to count] = ans

        return ans
    }

    private fun processInput(): List<Long> {
        return readFileLines("day11.txt")
            .joinToString("")
            .split(" ")
            .map { it.toLong() }
    }

    private fun Long.length() = when (this) {
        0L -> 1
        else -> log10(abs(toDouble())).toInt() + 1
    }

}