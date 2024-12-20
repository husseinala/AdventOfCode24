package solution

import utils.*
import utils.CharGridUtils.liesOn
import utils.GridDirections.nonDiagDirs
import java.util.ArrayDeque

object Day20 {

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        val input = processInput()

        val ans = findCheatingPaths(input, cheatPicoseconds = 2, stepsSaved = if (input.size == 15) 2 else 100)

        println("Part 1: $ans")
    }

    private fun part2() {
        val input = processInput()

        val ans = findCheatingPaths(input, cheatPicoseconds = 20, stepsSaved = if (input.size == 15) 50 else 100)

        println("Part 2: $ans")
    }

    private fun findCheatingPaths(input: List<CharArray>, cheatPicoseconds: Int, stepsSaved: Int): Int {
        var path = emptySet<Point>()

        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == 'S') {
                    path = findMainPath(i to j, input)
                    break
                }
            }
        }

        val pathMap = mutableMapOf<Point, Int>()
        val pathSize = path.size

        path.forEachIndexed { index, point -> pathMap[point] = pathSize - index }

        return path.mapIndexed { index, p ->
            findPaths(p, index, input, cheatPicoseconds, pathSize - stepsSaved, pathMap)
        }.sum()
    }

    private fun findMainPath(point: Point, map: List<CharArray>): Set<Point> {
        val queue = ArrayDeque<Set<Point>>()
        queue.offer(setOf(point))

        while (queue.isNotEmpty()) {
            val path = queue.poll()
            if (map[path.last()] == 'E') return path

            nonDiagDirs.map { it + path.last() }
                .filter { it liesOn map && !path.contains(it) && map[it] == '.' || map[it] == 'E' }
                .forEach {
                    queue.offer(path + it)
                }
        }

        return emptySet()
    }

    private fun findPaths(
        start: Point,
        startSteps: Int,
        map: List<CharArray>,
        picoseconds: Int,
        maxPoints: Int,
        endPoints: Map<Point, Int>,
    ): Int {
        val pq = ArrayDeque<Point>()

        var ans = 0

        pq.offer(start)

        var remSeconds = picoseconds
        var steps = 0

        val visited = mutableSetOf<Point>()

        while (remSeconds != 0 && pq.isNotEmpty()) {
            repeat(pq.size) {
                val p = pq.poll()
                nonDiagDirs.map { p + it }
                    .filter { it liesOn map && !visited.contains(it) }
                    .forEach {
                        if (endPoints.contains(it)) {
                            val totalPoints = startSteps + steps + endPoints[it]!!

                            if (totalPoints <= maxPoints) ans++
                        }

                        visited.add(it)
                        pq.offer(it)
                    }
            }

            steps++
            remSeconds--
        }

        return ans
    }

    private fun processInput(): List<CharArray> {
        return readFileLines("day20.txt").map { it.toCharArray() }
    }
}