package solution

import utils.GridDirections
import utils.readFileLines

object Day6 {

    fun solve() {
        val grid = processInput()
        val start = getStartingPosition(grid)

        part1(grid, start)
        part2(grid, start)
    }

    private fun part1(grid: List<CharArray>, start: Pair<Int, Int>) {
        val ans = simulate(grid, start)

        println("Part 1: $ans")
    }

    private fun part2(grid: List<CharArray>, start: Pair<Int, Int>) {
        var ans = 0

        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if ((i != start.first || j != start.second) && grid[i][j] == 'X') {
                    grid[i][j] = '#'
                    if (checkLoops(grid, start)) {
                        ans++
                        grid[i][j] = 'O'
                    }
                    grid[i][j] = 'X'
                }
            }
        }

        println("Part 2: $ans")
    }

    private fun getStartingPosition(grid: List<CharArray>): Pair<Int, Int> {
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (grid[i][j] == '^') {
                    return i to j
                }
            }
        }

        return 0 to 0
    }

    private fun simulate(
        grid: List<CharArray>,
        start: Pair<Int, Int>,
    ): Int {
        var pos = 0

        var (i, j) = start
        var dir = GridDirections.up

        while (i in grid.indices && j in grid[i].indices) {
            if (grid[i][j] != 'X') {
                pos++
                grid[i][j] = 'X'
            }

            var nextI = i + dir.first
            var nextJ = j + dir.second

            if (nextI !in grid.indices || nextJ !in grid[nextI].indices) break

            while (grid[nextI][nextJ] == '#') {
                dir = dir.getNextDir()
                nextI = i + dir.first
                nextJ = j + dir.second
            }

            i = nextI
            j = nextJ
        }

        return pos
    }

    private fun checkLoops(
        grid: List<CharArray>,
        start: Pair<Int, Int>,
    ): Boolean {
        var (i, j) = start
        var dir = GridDirections.up

        val seen = mutableSetOf<String>()

        while (i in grid.indices && j in grid[i].indices) {
            val key = "$i,$j,$dir"

            if (seen.contains(key)) return true

            seen.add(key)

            var nextI = i + dir.first
            var nextJ = j + dir.second

            if (nextI !in grid.indices || nextJ !in grid[nextI].indices) break

            while (grid[nextI][nextJ] == '#') {
                dir = dir.getNextDir()
                nextI = i + dir.first
                nextJ = j + dir.second
            }

            i = nextI
            j = nextJ
        }

        return false
    }

    private fun Pair<Int, Int>.getNextDir() = with(GridDirections) {
        when (this@getNextDir) {
            up -> right
            right -> down
            down -> left
            else -> up
        }
    }

    private fun processInput(): List<CharArray> {
        val grid = mutableListOf<CharArray>()
        readFileLines("day6.txt") {
            grid.add(it.toCharArray())
        }

        return grid
    }
}
