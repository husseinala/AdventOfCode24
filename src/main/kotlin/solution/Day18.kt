package solution

import utils.*
import java.util.ArrayDeque
import utils.GridDirections.nonDiagDirs
import kotlin.math.floor

object Day18 {

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        val coordinates = processInput()
        val end = if (coordinates.size == 25) 6 else 70
        val take = if (coordinates.size == 25) 12 else 1024

        val ans = bfs(coordinates.take(take).toMutableSet(), end = end to end)
        println("Part 1: $ans")
    }

    private fun part2() {
        val coordinates = processInput()
        val end = if (coordinates.size == 25) 6 else 70
        val start = if (coordinates.size == 25) 12 else 1024

        var low = start + 1
        var high = coordinates.size

        while (low < high) {
            val mid = floor((low + high) / 2.0).toInt()

            val ans = bfs(coordinates.take(mid).toMutableSet(), end = end to end)

            if (ans == -1) {
                high = mid
            } else {
                low = mid + 1
            }
        }

        val ans = coordinates[low - 1]

        println("Part 2: ${ans.x},${ans.y}")
    }

    private fun bfs(coordinates: MutableSet<Point>, end: Point): Int {
        val start = 0 to 0

        val queue = ArrayDeque<Point>()

        queue.offer(start)
        var count = 0
        while (queue.isNotEmpty()) {
            repeat(queue.size) {
                val p = queue.poll()

                if (p == end) return count

                nonDiagDirs.map { p + it }
                    .filter { it liesOn (start to end) && !coordinates.contains(it) }
                    .forEach {
                        coordinates.add(it)
                        queue.offer(it)
                    }
            }
            count++
        }

        return -1
    }

    private fun processInput(): List<Point> {
        return readFileLines("day18.txt").map {
            val (x, y) = it.split(",").map { it.toInt() }
            y to x
        }
    }
}