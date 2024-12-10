package solution

import utils.*
import utils.GridDirections.nonDiagDirs
import utils.ListGridUtils.liesOn

object Day10 {

    fun solve() {
        part1()
        part2()
    }

    private fun part1() {
        val ans = searchPaths(includeAllRoutes = false)

        println("Part 1: $ans")
    }

    private fun part2() {
        val ans = searchPaths(includeAllRoutes = true)

        println("Part 2: $ans")
    }

    private fun searchPaths(includeAllRoutes: Boolean): Int {
        var ans = 0

        val input = processInput()

        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == 0) {
                    ans += dfs(input, i to j, includeAllRoutes, mutableSetOf())
                }
            }
        }

        return ans
    }

    private fun dfs(grid: List<List<Int>>, start: Point, includeAllRoutes: Boolean, visited: MutableSet<String>): Int {
        if (!includeAllRoutes) {
            visited.add(start.toString())
        }

        if (grid[start] == 9) return 1

        return nonDiagDirs.map { start + it }
            .filter { it liesOn grid }
            .filter { !visited.contains(it.toString()) }
            .filter { grid[start] + 1 == grid[it] }
            .sumOf { dfs(grid, it, includeAllRoutes, visited) }
    }

    private fun processInput(): List<List<Int>> {
        return readFileLines("day10.txt").map { line -> line.map { it.digitToInt() } }
    }

}