package solution

import utils.Point
import utils.readFileLines
import utils.x
import utils.y

private data class Robot(
    val position: Point,
    val velocity: Point
)

object Day14 {

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        val input = processInput()

        val qCounts = IntArray(4)

        input.forEach {
            val q = calculate(robot = it, seconds = 100)
            if (q != -1) qCounts[q]++
        }

        val ans = qCounts.fold(1) { acc, i -> acc * i }

        println("Part 1: $ans")
    }

    private fun part2() {
        val input = processInput()

        println("Part 2: ")
    }

    private fun calculate(
        robot: Robot,
        seconds: Int = 0,
        width: Int = 11,
        height: Int = 7,
    ): Int {
        val vx = (robot.velocity.x * seconds) % width
        val vy = (robot.velocity.y * seconds) % height

        var rx = robot.position.x
        var ry = robot.position.y

        val halfWidth = (width - 1) / 2
        val halfHeight = (height - 1) / 2

        val quadrants = listOf(
            (0 to halfWidth - 1) to (0 to halfHeight - 1),
            (halfWidth + 1 to width - 1) to (0 to halfHeight - 1),
            (0 to halfWidth - 1) to (halfHeight + 1 to height - 1),
            (halfWidth + 1 to width - 1) to (halfHeight + 1 to height - 1),
        )

        rx += vx
        ry += vy

        if (rx < 0) rx += width
        if (ry < 0) ry += height
        if (rx >= width) rx -= width
        if (ry >= height) ry -= height

        return quadrants.indexOfFirst { (x, y) -> rx in x.first..x.second && ry in y.first..y.second }
    }

    private fun processInput(): List<Robot> {
        val numRegex = Regex("(?=[+-])|[\\D+]")

        return readFileLines("day14.txt").map { line ->
            val (px, py, vx, vy) = line.split(numRegex).filter { it.isNotEmpty() }.map { it.toInt() }

            Robot(position = py to px, velocity = vy to vx)
        }

    }
}