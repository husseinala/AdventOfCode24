package solution

import utils.*
import java.util.ArrayDeque
import utils.GridDirections.nonDiagDirs

object Day18 {

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        val coordinates = processInput()
        val end = if (coordinates.size == 25) 6 else 70
        val take = if (coordinates.size == 25) 12 else 1024

        val ans = bfs(coordinates.take(take).toMutableList(), end = end to end)
        println("Part 1: ${ans.size - 1}")
    }

    private fun part2() {
        val coordinates = processInput()
        val end = if (coordinates.size == 25) 6 else 70
        val start = if (coordinates.size == 25) 12 else 1024

        var previousPath = bfs(coordinates.take(start).toMutableList(), end = end to end)

        val ans = (start + 1..coordinates.size).first { take ->
            if (previousPath.contains(coordinates[take - 1])) {
                previousPath = bfs(coordinates.take(take).toMutableList(), end = end to end)
            }

            previousPath.isEmpty()
        }.let { coordinates[it - 1] }

        println("Part 2: ${ans.x},${ans.y}")
    }

    private fun bfs(coordinates: MutableList<Point>, end: Point): Set<Point> {
        val start = 0 to 0

        val queue = ArrayDeque<List<Point>>()

        queue.offer(listOf(start))
        while (queue.isNotEmpty()) {
            val p = queue.poll()

            if (p.last() == end) return p.toSet()

            nonDiagDirs.map { p.last() + it }
                .filter { it liesOn (start to end) && !coordinates.contains(it) }
                .forEach {
                    coordinates.add(it)
                    queue.offer(p + it)
                }
        }

        return emptySet()
    }

    private fun processInput(): List<Point> {
        return readFileLines("day18.txt").map {
            val (x, y) = it.split(",").map { it.toInt() }
            y to x
        }
    }
}