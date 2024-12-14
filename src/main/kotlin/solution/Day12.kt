package solution

import utils.*
import utils.GridDirections.down
import utils.GridDirections.left
import utils.GridDirections.nonDiagDirs
import utils.GridDirections.right
import utils.GridDirections.up
import utils.CharGridUtils.liesOn

object Day12 {

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        var ans = 0L

        val map = processInput()
        val visited = Array(map.size) { BooleanArray(map[it].size) }

        for (i in map.indices) {
            for (j in map[i].indices) {
                if (!visited[i][j]) {
                    val fencing = mutableSetOf<Pair<Point, Point>>()
                    val area = calculateAreaAndPerimeter(
                        i to j,
                        map,
                        visited,
                        fencing,
                    )

                    //println("A region of ${map[i][j]} plants with price $area * ${fencing.size} = ${area*p}.")

                    ans += area * fencing.size
                }
            }
        }

        println("Part 1: $ans")
    }

    private fun part2() {
        var ans = 0L

        val map = processInput()
        val visited = Array(map.size) { BooleanArray(map[it].size) }

        for (i in map.indices) {
            for (j in map[i].indices) {
                if (!visited[i][j]) {
                    val fencing = mutableSetOf<Pair<Point, Point>>()
                    val area = calculateAreaAndPerimeter(i to j, map, visited, fencing)

                    fun clearFencing(p: Point, dir: Point, f: MutableSet<Pair<Point, Point>>) {
                        f.remove(p to dir)

                        if (dir.j != 0) {
                            if (f.contains(p + up to dir)) clearFencing(p + up, dir, f)
                            if (f.contains(p + down to dir)) clearFencing(p + down, dir, f)
                        } else {
                            if (f.contains(p + right to dir)) clearFencing(p + right, dir, f)
                            if (f.contains(p + left to dir)) clearFencing(p + left, dir, f)
                        }
                    }

                    var sides = 0
                    while (fencing.isNotEmpty()) {
                        sides++
                        val (p, dir) = fencing.first()
                        clearFencing(p, dir, fencing)
                    }

                    ans += area * sides
                }
            }
        }

        println("Part 2: $ans")
    }

    private fun calculateAreaAndPerimeter(
        p: Point,
        map: List<CharArray>,
        visited: Array<BooleanArray>,
        fencing: MutableCollection<Pair<Point, Point>>,
    ): Int {
        visited[p.i][p.j] = true

        var area = 1

        nonDiagDirs
            .map { dir -> p + dir }
            .forEach {
                if (!(it liesOn map) || map[p] != map[it]) {
                    val dir = it - p
                    fencing.add(it to dir)
                } else if (!visited[it.i][it.j]) {
                    area += calculateAreaAndPerimeter(it, map, visited, fencing)
                }
            }

        return area
    }

    private fun processInput(): List<CharArray> {
        return readFileLines("day12.txt")
            .map { line -> line.toCharArray() }
    }

}