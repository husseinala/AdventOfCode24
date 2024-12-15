package solution

import utils.*
import utils.CharGridUtils.liesOn
import utils.GridDirections.down
import utils.GridDirections.left
import utils.GridDirections.right
import utils.GridDirections.up

object Day15 {

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        val (map, directions) = processInput()
        val ans = findCoordinates(map, directions)

        println("Part 1: $ans")
    }

    private fun part2() {
        val (map, directions) = processInput().let {
            it.copy(first = it.first.map { line ->
                line.flatMap { c -> c.expand().toList() }.toCharArray()
            }, second = it.second)
        }

        val ans = findCoordinates(map, directions)

        println("Part 2: $ans")
    }

    private fun findCoordinates(map: List<CharArray>, directions: String): Long {
        var robotPosition = 0 to 0

        for (i in map.indices) {
            for (j in map.indices) {
                if (map[i][j] == '@') {
                    robotPosition = i to j
                    break
                }
            }
        }

        directions.map { it.toDirection() }.forEach { dir ->
            if (canMoveRec(robotPosition, map, dir)) {
                moveRec(robotPosition, map, dir)
                robotPosition += dir
            }
        }

        var ans = 0L

        for (i in map.indices) {
            for (j in map[i].indices) {
                if (map[i][j] == 'O' || map[i][j] == '[') {
                    ans += (i * 100) + j
                }
            }
        }

        return ans
    }

    private fun canMoveRec(start: Point, map: List<CharArray>, dir: Point): Boolean {
        val nextPoint = start + dir

        if (
            nextPoint liesOn map && map[nextPoint] != '#'
        ) {
            if (map[nextPoint] == '.') return true

            return if (dir == left || dir == right || map[nextPoint] == 'O') {
                canMoveRec(nextPoint, map, dir)
            } else if (map[nextPoint] == '[') {
                canMoveRec(nextPoint, map, dir) && canMoveRec(nextPoint + right, map, dir)
            } else {
                canMoveRec(nextPoint, map, dir) && canMoveRec(nextPoint + left, map, dir)
            }
        }

        return false
    }

    private fun moveRec(start: Point, map: List<CharArray>, dir: Point) {
        val nextPoint = start + dir

        if (nextPoint liesOn map && map[nextPoint] != '.') {
            if (dir == left || dir == right || map[nextPoint] == 'O') {
                moveRec(nextPoint, map, dir)
            } else if (map[nextPoint] == '[') {
                moveRec(nextPoint, map, dir)
                moveRec(nextPoint + right, map, dir)
            } else {
                moveRec(nextPoint, map, dir)
                moveRec(nextPoint + left, map, dir)
            }
        }

        map[nextPoint] = map[start]
        map[start] = '.'
    }

    private fun Char.toDirection() = when (this) {
        '>' -> right
        '^' -> up
        '<' -> left
        'v' -> down
        else -> throw Exception("Invalid direction")
    }

    private fun Char.expand() = when (this) {
        'O' -> "[]"
        '@' -> "@."
        else -> "$this$this"
    }

    private fun processInput(): Pair<List<CharArray>, String> {
        val map = mutableListOf<CharArray>()
        val directions = StringBuilder()

        var processingDirections = false

        readFileLines("day15.txt") { line ->
            if (line.isEmpty()) {
                processingDirections = true
            } else if (processingDirections) {
                directions.append(line)
            } else {
                map.add(line.toCharArray())
            }
        }

        return map to directions.toString()
    }

}