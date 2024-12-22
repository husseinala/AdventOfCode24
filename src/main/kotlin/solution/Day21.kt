package solution

import utils.*
import utils.CharGridUtils.liesOn
import utils.GridDirections.left
import utils.GridDirections.nonDiagDirs
import utils.GridDirections.right
import utils.GridDirections.up
import java.util.PriorityQueue

object Day21 {

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        println("Part 1: ${getComplexitiesForDepth(depth = 1)}")
    }

    private fun part2() {
        println("Part 2: ${getComplexitiesForDepth(depth = 24)}")
    }

    private fun getComplexitiesForDepth(depth: Int): Long {
        val input = processInput()

        return input.sumOf { pin ->
            pin.dropLast(1).toInt() * getDirectionsForPin(pin).minOf { dir ->
                "A$dir".zipWithNext { a, b -> getMinSteps(from = a, to = b, depth) }.sum()
            }
        }
    }

    private fun getDirectionsForPin(pin: String): List<String> {
        return "A$pin".zipWithNext { a, b -> getPadDirections(from = a, to = b, codePad) }.fold(listOf("")) { acc, s ->
            acc.flatMap { pre -> s.map { next -> pre + next } }
        }
    }

    private val stepsMemo = mutableMapOf<String, Long>()
    private fun getMinSteps(from: Char, to: Char, depth: Int): Long {
        if (depth == 0) return getPadDirections(from, to, dirPad).first().length.toLong()

        val key = "$from-$to-$depth"

        return stepsMemo.getOrPut(key) {
            getPadDirections(from, to, dirPad).minOf {
                "A$it".zipWithNext { a, b -> getMinSteps(from = a, to = b, depth - 1) }.sum()
            }
        }
    }

    private val directionsMemo = mutableMapOf<Pair<Char, Char>, List<String>>()
    private fun getPadDirections(from: Char, to: Char, pad: List<CharArray>) = directionsMemo.getOrPut(from to to) {
        val ans = mutableListOf<String>()
        val pq = PriorityQueue<Pair<Point, String>> { a, b -> a.second.length.compareTo(b.second.length) }

        var start = 0 to 0
        for (i in pad.indices) {
            for (j in pad[i].indices) {
                if (pad[i][j] == from) {
                    start = i to j
                    break
                }
            }
        }

        pq.offer(start to "")

        while (pq.isNotEmpty()) {
            val (point, directions) = pq.poll()

            if (pad[point] == to) {
                if (ans.isEmpty() || ans.last().length - 1 == directions.length) {
                    ans += (directions + "A")
                    continue
                } else {
                    break
                }
            }

            if (ans.isNotEmpty() && ans.last().length <= directions.length) continue

            nonDiagDirs
                .forEach { dir ->
                    val p = point + dir
                    if (p liesOn pad && pad[p] != ' ') {
                        pq.offer(p to directions + dir.symbol)
                    }
                }
        }

        ans
    }

    private val codePad = listOf(
        charArrayOf('7', '8', '9'),
        charArrayOf('4', '5', '6'),
        charArrayOf('1', '2', '3'),
        charArrayOf(' ', '0', 'A'),
    )

    private val dirPad = listOf(
        charArrayOf(' ', '^', 'A'),
        charArrayOf('<', 'v', '>'),
    )

    private val Point.symbol
        get() = when (this) {
            right -> '>'
            left -> '<'
            up -> '^'
            else -> 'v'
        }

    private fun processInput(): List<String> {
        return readFileLines("day21.txt")
    }
}
