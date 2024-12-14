package solution

import utils.*
import utils.GridDirections.down
import utils.GridDirections.right
import utils.CharGridUtils.liesOn
import kotlin.math.max

private data class Robot(
    val position: Point,
    val velocity: Point
)

object Day14 {

    private const val width = 101
    private const val height = 103

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        val input = processInput()

        val qCounts = IntArray(4)

        input.forEach {
            val position = calculatePositionAfter(robot = it, seconds = 100)
            val q = getQuadrant(position)
            if (q != -1) qCounts[q]++
        }

        val ans = qCounts.fold(1) { acc, i -> acc * i }

        println("Part 1: $ans")
    }

    private fun part2() {
        val input = processInput()

        var seconds = 0
        while (true) {
            val map = Array(height) { CharArray(width) { ' ' } }.toList()
            val visited = Array(height) { BooleanArray(width) }

            input.forEach {
                val position = calculatePositionAfter(it, seconds)

                map[position.i][position.j] = '.'
            }

            var maxConnected = 0

            for (i in 0 until height) {
                for (j in 0 until width) {
                    maxConnected = max(maxConnected, getConnectedPointsCount(i to j, map, visited))
                }
            }

            // find the first set of connected points and hope it's the tree
            if (maxConnected > 50) {
                println("After $seconds")
                map.forEach {
                    println(it.joinToString(""))
                }
                break
            }

            seconds++
        }

        println("Part 2: $seconds")
    }

    private fun calculatePositionAfter(robot: Robot, seconds: Int = 0): Point {
        val vx = (robot.velocity.x * seconds) % width
        val vy = (robot.velocity.y * seconds) % height

        var rx = robot.position.x
        var ry = robot.position.y

        rx += vx
        ry += vy

        if (rx < 0) rx += width
        if (ry < 0) ry += height
        if (rx >= width) rx -= width
        if (ry >= height) ry -= height

        return ry to rx
    }

    private fun getQuadrant(position: Point): Int {
        val halfWidth = (width - 1) / 2
        val halfHeight = (height - 1) / 2

        val quadrants = listOf(
            (0 to halfWidth - 1) to (0 to halfHeight - 1),
            (halfWidth + 1 to width - 1) to (0 to halfHeight - 1),
            (0 to halfWidth - 1) to (halfHeight + 1 to height - 1),
            (halfWidth + 1 to width - 1) to (halfHeight + 1 to height - 1),
        )

        return quadrants.indexOfFirst { (x, y) ->
            position.x in x.first..x.second && position.y in y.first..y.second
        }
    }

    private fun getConnectedPointsCount(point: Point, map: List<CharArray>, visited: Array<BooleanArray>): Int {
        visited[point.i][point.j] = true
        return 1 + listOf(right, down)
            .map { point + it }
            .filter { it liesOn map && map[it] != ' ' && !visited[it.i][it.j]}
            .sumOf { getConnectedPointsCount(it, map, visited) }
    }

    private fun processInput(): List<Robot> {
        val numRegex = Regex("(?=[+-])|[\\D+]")

        return readFileLines("day14.txt").map { line ->
            val (px, py, vx, vy) = line.split(numRegex).filter { it.isNotEmpty() }.map { it.toInt() }

            Robot(position = py to px, velocity = vy to vx)
        }

    }
}