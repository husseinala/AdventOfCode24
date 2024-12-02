package solution

import utils.readFileLines
import java.util.*
import kotlin.math.abs

object Day1 {
    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        val pq1 = PriorityQueue<Int>()
        val pq2 = PriorityQueue<Int>()

        processInput {a, b ->
            pq1.offer(a)
            pq2.offer(b)
        }

        var ans = 0

        while (pq1.isNotEmpty()) {
            ans += abs(pq1.poll() - pq2.poll())
        }

        println("Part 1: $ans")
    }

    private fun part2() {
        val freq = mutableMapOf<Int, Int>()
        val nums = mutableListOf<Int>()

        processInput { a, b ->
            freq[b] = (freq[b] ?: 0) + 1
            nums.add(a)
        }

        var ans = 0L

        nums.forEach { n ->
            ans += n * (freq[n] ?: 0)
        }

        println("Part 2: $ans")
    }

    private fun processInput(action : (a: Int, b: Int) -> Unit) {
        readFileLines("day1.txt") {
            val (a, b) = it.split("   ")

            action(a.toInt(), b.toInt())
        }
    }
}